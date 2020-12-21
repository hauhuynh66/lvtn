package com.lvtn.service;

import com.lvtn.model.*;
import com.lvtn.repository.DHTRepository;
import com.lvtn.repository.MiscRepository;
import com.lvtn.repository.RoomDeviceRepository;
import com.lvtn.repository.RoomRepository;
import com.lvtn.util.Formatter;
import com.lvtn.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class DataService {
    private static String TAG = "Data Service";
    static private Logger log = LogManager.getLogger();
    @Autowired
    private DHTRepository dhtRepository;
    @Autowired
    private MiscRepository miscRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomDeviceRepository roomDeviceRepository;

    public long count(){
        return roomRepository.count();
    }

    public Room getRoom(int id){
        return roomRepository.findById(id);
    }

    public List<Misc> getMiscList(int roomId){
        Room room = roomRepository.findById(roomId);
        if(room !=null){
            return miscRepository.getTop20ByRoomOrderByDateDesc(room);
        }else{
            return new ArrayList<>();
        }
    }

    public List<DHT> getDHTList(int roomId){
        Room room = roomRepository.findById(roomId);
        if(room !=null){
            return dhtRepository.getTop20ByRoomOrderByDateDesc(room);
        }else{
            return new ArrayList<>();
        }
    }

    public List<RoomDevice> getDeviceList(int id){
        return roomDeviceRepository.getAllByRoom(getRoom(id));
    }

    public DHT getTopDHT(int roomId){
        Room room = roomRepository.findById(roomId);
        if(room !=null){
            return dhtRepository.getTopByRoomOrderByDateDesc(room);
        }else{
            return null;
        }
    }

    public Misc getTopMisc(int roomId){
        Room room = roomRepository.findById(roomId);
        if(room !=null){
            return miscRepository.getTopByRoomOrderByDateDesc(room);
        }else{
            return null;
        }
    }

    private List<TimeInfo> averageDHT(Room room, Date from, Date to){
        List<TimeInfo> rps = new ArrayList<>();
        List<DHT> dht = dhtRepository.getAllByRoomAndDateGreaterThanAndDateLessThan(room, from, to);
        double aT = 0,aH = 0;
        if(!dht.isEmpty()){
            for(DHT dt:dht){
                aT += dt.getTemp();
                aH += dt.getHumid();
            }
            aT /= dht.size();aH /= dht.size();
            TimeInfo rT = new TimeInfo(Formatter.formatNumber(aT, 3), null, "Average Temperature");
            TimeInfo rH = new TimeInfo(Formatter.formatNumber(aH, 3), null, "Average Humidity");
            rps.add(rT);rps.add(rH);
        }
        return rps;
    }

    private List<TimeInfo> averageMisc(Room room, Date from, Date to){
        List<TimeInfo> rps = new ArrayList<>();
        List<Misc> misc = miscRepository.getAllByRoomAndDateGreaterThanAndDateLessThan(room, from, to);
        double aS = 0,aL = 0;
        if(!misc.isEmpty()){
            for(Misc m:misc){
                aS += m.getSmoke();
                aL += m.getLight();
            }
            aS /= misc.size();aL /= misc.size();
            TimeInfo rS = new TimeInfo(Formatter.formatNumber(aS, 3), null, "Average Smoke Density");
            TimeInfo rL = new TimeInfo(Formatter.formatNumber(aL, 3), null, "Average Light Intensity");
            rps.add(rS);rps.add(rL);
        }
        return rps;
    }

    private Map<String, Double> maxDiff(Room room, Date from, Date to){
        return new HashMap<>();
    }

    private Map<String, Double> minDiff(Room room, Date from, Date to){
        return new HashMap<>();
    }

    private Map<String, Double> averageDiff(Room room, Date from, Date to){
        return new HashMap<>();
    }

    public TimeReport basicReport(int roomId, Date from, Date to){
        Room room = roomRepository.findById(roomId);
        TimeReport report = new TimeReport(from, to, new ArrayList<>());
        if(room !=null){
            DHT maxT = dhtRepository.getTopByRoomAndDateGreaterThanAndDateLessThanOrderByTempDesc(room, from, to);
            DHT maxH = dhtRepository.getTopByRoomAndDateGreaterThanAndDateLessThanOrderByHumidDesc(room, from, to);
            if(maxT!=null){
                report.getList().add(new TimeInfo(maxT.getTemp(), maxT.getDate(), "Max Temperature"));
            }
            if(maxH!=null){
                report.getList().add(new TimeInfo(maxH.getHumid(), maxH.getDate(), "Max Humidity"));
            }
            Misc maxS = miscRepository.getTopByRoomAndDateGreaterThanAndDateLessThanOrderBySmokeDesc(room, from, to);
            Misc maxL = miscRepository.getTopByRoomAndDateGreaterThanAndDateLessThanOrderByLightDesc(room, from, to);
            if(maxS!=null){
                report.getList().add(new TimeInfo(maxS.getSmoke(), maxS.getDate(), "Max Smoke Density"));
            }
            if(maxL!=null){
                report.getList().add(new TimeInfo(maxL.getLight(), maxL.getDate(), "Max Light Intensity"));
            }
        }
        return report;
    }

    public ListReport extraReport(int roomId, Date from, Date to){
        Room room = roomRepository.findById(roomId);
        ListReport report = new ListReport(from, to, new ArrayList<>());
        if(room !=null) {
            report.setFrom(from); report.setTo(to);
            try {
                File file = new ClassPathResource("setting/"+ room.getId()+".json").getFile();
                RS rs = Utils.getFromFile(file);
                assert rs!=null;
                ListInfo infoT = new ListInfo("Temperature Greater Than Standard Value");
                infoT.setDHT(dhtRepository.
                        getAllByRoomAndDateGreaterThanAndDateLessThanAndTempGreaterThan(room, from, to, rs.getT()), 1);
                ListInfo infoH = new ListInfo("Humidity Greater Than Standard Value");
                infoH.setDHT(dhtRepository
                        .getAllByRoomAndDateGreaterThanAndDateLessThanAndHumidGreaterThan(room, from, to, rs.getH()), 2);
                ListInfo infoS = new ListInfo("Smoke Density Greater Than Standard Value");
                infoS.setMisc(miscRepository.
                        getAllByRoomAndDateGreaterThanAndDateLessThanAndSmokeGreaterThan(room, from, to, rs.getS()), 1);
                ListInfo infoL = new ListInfo("Light Intensity Greater Than Standard Value");
                infoL.setMisc(miscRepository
                        .getAllByRoomAndDateGreaterThanAndDateLessThanAndLightGreaterThan(room, from, to, rs.getL()), 2);
                report.getList().add(infoT);
                report.getList().add(infoH);
                report.getList().add(infoS);
                report.getList().add(infoL);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        return report;
    }

    public boolean add(int roomId, JsonObject object){
        Room room = getRoom(roomId);
        if(room ==null){
            return false;
        }else{
            DHT dht = new DHT(object.getTemp().getTemp(), object.getHumid().getHumid(), object.getTime(), room);
            dhtRepository.save(dht);
            Misc misc = new Misc(object.getSmoke().getSmoke(), object.getLight().getLight(), object.getTime(), room);
            miscRepository.save(misc);
            return true;
        }
    }

    private RoomDevice findDevice(int roomId, String nid){
        Room room = getRoom(roomId);
        return  roomDeviceRepository.findByRoomAndAndNid(room, nid);
    }

    public boolean changeState(int roomId, String nid){
        RoomDevice device = findDevice(roomId, nid);
        if(device!=null){
            device.setStatus(!device.isStatus());
            roomDeviceRepository.save(device);
            return true;
        }else {
            return false;
        }
    }
}