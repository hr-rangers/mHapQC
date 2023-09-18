package com;

import com.entity.CpgCovArgs;
import com.entity.Region;
import com.utils.Util;
import com.utils.bigwigTool.BBFileReader;
import com.utils.bigwigTool.BigWigIterator;
import com.utils.bigwigTool.WigItem;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class CpgCov {
//    static Util util = new Util();
//
//    public void readBB(String path) throws Exception {
//        List<Region> bedRegionList = util.getBedRegionList("hg19_cpgisland.bed");
//        BBFileReader reader = new BBFileReader(path);
//        int value = 0;
//        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
//        float percentage = 0;
//        String cpgpath = "hg19_CpG.gz";
//        for (Region region : bedRegionList) {
//            List<Integer> cpgPosListInRegion = util.parseCpgFile(cpgpath, region);
//            if (cpgPosListInRegion.size() < 1)
//                continue;
//            for (Integer cpgPos : cpgPosListInRegion) {
//                BigWigIterator iter = reader.getBigWigIterator(region.getChrom(), cpgPos - 1, region.getChrom(), cpgPos, true);
//                while (iter.hasNext()) {
//                    WigItem wigItem = iter.next();
//                    value = (int) wigItem.getWigValue();
//                    if (value == 0) continue;
//                    map.put(value, map.getOrDefault(value, 0) + 1);
//                }
//            }
//        }
//        int count = 0;
//        int count2 = 0;
//        int count3 = 0;
//        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
//        treeMap.put(1, map.get(1));
//        for (int i = 1; i <= map.size(); i++) {
//            if (map.containsKey(i)) {
//                count += map.get(i);
//                if (i % 5 == 0) {
//                    count2 += count;
//                    treeMap.put(i, count2);
//                    count = 0;
//                }
//            }
//        }
//        count3 = count2;
//        System.out.println(count3);
//        System.out.println(treeMap);
//
//        for (int i : treeMap.keySet()) {
//            percentage = (float) count3 / (float) count2;
//            if (i == 1) {
//                count3 -= treeMap.get(i);
//            }
//            else if (i==5)
//                count3 = count3 - treeMap.get(i) + treeMap.get(i/5);
//            else {
//                count3 = count3 - treeMap.get(i) + treeMap.get((i / 5 - 1) * 5);
//            }
//            System.out.println(i + "\t" + String.format("%.4f", percentage));
//        }
//    }
static Util util = new Util();
    CpgCovArgs args = new CpgCovArgs();


    public void parseCpgCovArgs(CpgCovArgs cpgCovArgs) throws Exception {
        args = cpgCovArgs;
        List<Region> bedRegionList = util.getBedRegionList(args.getBedPath());
        BBFileReader reader = new BBFileReader(args.getBigwig());
        int value = 0;
        BufferedWriter bufferedWriter = util.createOutputFile("", args.getTag() + ".txt");
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        float percentage = 0;
        for (Region region : bedRegionList) {
            List<Integer> cpgPosListInRegion = util.parseCpgFile(args.getCpgPath(), region);
            if (cpgPosListInRegion.size() < 1)
                continue;
            for (Integer cpgPos : cpgPosListInRegion) {
                BigWigIterator iter = reader.getBigWigIterator(region.getChrom(), cpgPos - 1, region.getChrom(), cpgPos, true);
                while (iter.hasNext()) {
                    WigItem wigItem = iter.next();
                    value = (int) wigItem.getWigValue();
                    if (value == 0 ) continue;
                    map.put(value, map.getOrDefault(value, 0) + 1);
                }
            }
        }
        int count = 0;
        int count2 = 0;
        int count3 = 0;
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(1, map.get(1));
        for (int i = 1; i <= map.size(); i++) {
            if (map.containsKey(i)) {
                count += map.get(i);
                if (i % 5 == 0) {
                    count2 += count;
                    treeMap.put(i, count2);
                    count = 0;
                }
            }
        }
        count3 = count2;

        for (int i : treeMap.keySet()) {
            percentage = (float) count3 / (float) count2;
            if (i == 1) {
                count3 -= treeMap.get(i);
            }
            else if (i==5)
                count3 = count3 - treeMap.get(i) + treeMap.get(i/5);
            else {
                count3 = count3 - treeMap.get(i) + treeMap.get((i / 5 - 1) * 5);
            }

            bufferedWriter.write(i + "\t" + String.format("%.4f", percentage)+"\n");
        }
    bufferedWriter.close();
    }

}