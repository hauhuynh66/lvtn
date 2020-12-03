package com.lvtn.util;

import com.lvtn.model.DHT;
import com.lvtn.model.House;
import com.lvtn.model.Misc;
import com.lvtn.model.User;
import com.lvtn.repository.DHTRepository;
import com.lvtn.repository.HouseRepository;
import com.lvtn.repository.MiscRepository;
import com.lvtn.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CMDRunner implements CommandLineRunner {
    private static final String TAG = "Command Line Runner";
    private static Logger log = LogManager.getLogger();
    private static final int N = 100;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DHTRepository dhtRepository;
    @Autowired
    private MiscRepository miscRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private Utils utils;
    @Override
    public void run(String... args) throws Exception {
        User u1 = new User("norman","norman@gmail.com",
                utils.encoder(5).encode("Norman"));
        User u2 = new User("hauhuynh","hauhuynh66@gmail.com",
                utils.encoder(5).encode("Hauhuynh"));
        userRepository.save(u1);
        userRepository.save(u2);
        populate();
        log.info("DONE");
    }

    private void populate(){
        init();
        List<House> houses = houseRepository.findAll();
        DHT[] dhts = new DHT[N];
        Misc[] msc = new Misc[N];
        Random random = new Random();
        Date now = new Date();
        for (int i=0; i<N; i++){
            double t = 25 + random.nextInt(25);
            double h = 75 + random.nextInt(20);
            double s = 100 + random.nextInt(250);
            double l = random.nextInt(1000);
            Date d = new Date(now.getTime()+300000);
            dhts[i] = new DHT(t, h, d, houses.get(random.nextInt(4)));
            msc[i] = new Misc(s, l, d, houses.get(random.nextInt(4)));
            dhtRepository.save(dhts[i]);
            miscRepository.save(msc[i]);
            now = d;
        }
    }

    private void init(){
        House[] houses = new House[4];
        houses[0] = new House("One");
        houseRepository.save(houses[0]);
        houses[1] = new House("Two");
        houseRepository.save(houses[1]);
        houses[2] = new House("Three");
        houseRepository.save(houses[2]);
        houses[3] = new House("Four");
        houseRepository.save(houses[3]);
    }
}
