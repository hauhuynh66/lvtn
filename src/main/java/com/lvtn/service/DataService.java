package com.lvtn.service;

import com.lvtn.model.DHT;
import com.lvtn.model.House;
import com.lvtn.model.Misc;
import com.lvtn.repository.DHTRepository;
import com.lvtn.repository.HouseRepository;
import com.lvtn.repository.MiscRepository;
import com.lvtn.util.Formatter;
import com.lvtn.util.Report;
import com.lvtn.util.ReportInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataService {
    private static String TAG = "Data Service";
    static private Logger log = LogManager.getLogger();
    @Autowired
    private DHTRepository dhtRepository;
    @Autowired
    private MiscRepository miscRepository;
    @Autowired
    private HouseRepository houseRepository;

    public long count(){
        return houseRepository.count();
    }

    public House getHouse(int id){
        return houseRepository.findById(id);
    }

    public List<Misc> getMiscList(int houseId){
        House house = houseRepository.findById(houseId);
        if(house!=null){
            return miscRepository.getTop20ByHouseOrderByDateDesc(house);
        }else{
            return new ArrayList<>();
        }
    }

    public List<DHT> getDHTList(int houseId){
        House house = houseRepository.findById(houseId);
        if(house!=null){
            return dhtRepository.getTop20ByHouseOrderByDateDesc(house);
        }else{
            return new ArrayList<>();
        }
    }

    public DHT getTopDHT(int houseId){
        House house = houseRepository.findById(houseId);
        if(house!=null){
            return dhtRepository.getTopByHouseOrderByDateDesc(house);
        }else{
            return null;
        }
    }

    public Misc getTopMisc(int houseId){
        House house = houseRepository.findById(houseId);
        if(house!=null){
            return miscRepository.getTopByHouseOrderByDateDesc(house);
        }else{
            return null;
        }
    }

    private List<ReportInfo> averageDHT(House house, Date from, Date to){
        List<ReportInfo> rps = new ArrayList<>();
        List<DHT> dht = dhtRepository.getAllByHouseAndDateGreaterThanAndDateLessThan(house, from, to);
        double aT = 0,aH = 0;
        if(!dht.isEmpty()){
            for(DHT dt:dht){
                aT += dt.getTemp();
                aH += dt.getHumid();
            }
            aT /= dht.size();aH /= dht.size();
            ReportInfo rT = new ReportInfo(Formatter.formatNumber(aT, 3), null, "Average Temperature");
            ReportInfo rH = new ReportInfo(Formatter.formatNumber(aH, 3), null, "Average Humidity");
            rps.add(rT);rps.add(rH);
        }
        return rps;
    }

    private List<ReportInfo> averageMisc(House house, Date from, Date to){
        List<ReportInfo> rps = new ArrayList<>();
        List<Misc> misc = miscRepository.getAllByHouseAndDateGreaterThanAndDateLessThan(house, from, to);
        double aS = 0,aL = 0;
        if(!misc.isEmpty()){
            for(Misc m:misc){
                aS += m.getSmoke();
                aL += m.getLight();
            }
            aS /= misc.size();aL /= misc.size();
            ReportInfo rS = new ReportInfo(Formatter.formatNumber(aS, 3), null, "Average Smoke Density");
            ReportInfo rL = new ReportInfo(Formatter.formatNumber(aL, 3), null, "Average Light Intensity");
            rps.add(rS);rps.add(rL);
        }
        return rps;
    }

    public Report basicReport(int houseId){
        House house = houseRepository.findById(houseId);
        Report report = new Report(new ArrayList<>());
        if(house!=null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date from = format.parse("2020-11-12");
                Date to = new Date(from.getTime() + 86400000);
                report.setFrom(from); report.setTo(to);
                DHT maxT = dhtRepository.getTopByHouseAndDateGreaterThanAndDateLessThanOrderByTempDesc(house, from, to);
                DHT maxH = dhtRepository.getTopByHouseAndDateGreaterThanAndDateLessThanOrderByHumidDesc(house, from, to);
                if(maxT!=null){
                    report.getRp().add(new ReportInfo(maxT.getTemp(), maxT.getDate(), "Max Temperature"));
                }
                if(maxH!=null){
                    report.getRp().add(new ReportInfo(maxH.getHumid(), maxH.getDate(), "Max Humidity"));
                }
                Misc maxS = miscRepository.getTopByHouseAndDateGreaterThanAndDateLessThanOrderBySmokeDesc(house, from, to);
                Misc maxL = miscRepository.getTopByHouseAndDateGreaterThanAndDateLessThanOrderByLightDesc(house, from, to);
                if(maxS!=null){
                    report.getRp().add(new ReportInfo(maxS.getSmoke(), maxS.getDate(), "Max Smoke Density"));
                }
                if(maxL!=null){
                    report.getRp().add(new ReportInfo(maxL.getLight(), maxL.getDate(), "Max Light Intensity"));
                }
            }catch (ParseException e){
                log.info(e.getMessage());
            }
        }
        return report;
    }

    public Report extraReport(int houseId){
        House house = houseRepository.findById(houseId);
        Report report = new Report(new ArrayList<>());
        if(house!=null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date from = format.parse("2020-11-02");
                Date to = new Date(from.getTime() + 86400000);
                report.setFrom(from); report.setTo(to);
                List<DHT> oT = dhtRepository.getAllByHouseAndDateGreaterThanAndDateLessThanAndTempGreaterThan(house, from, to, 30);
                List<DHT> oH = dhtRepository.getAllByHouseAndDateGreaterThanAndDateLessThanAndHumidGreaterThan(house, from, to, 70);
                report.getRp().add(new ReportInfo(oT.size(), null, "nT"));
                report.getRp().add(new ReportInfo(oH.size(), null, "nH"));
            } catch (ParseException e) {
                log.info(e.getMessage());
            }
        }
        return report;
    }

    public boolean add(House house, DHT dht, Misc msc){
        if(house==null){
            return false;
        }else{
            dhtRepository.save(dht);
            miscRepository.save(msc);
            return true;
        }
    }
}