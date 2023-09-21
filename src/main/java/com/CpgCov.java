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

    static Util util = new Util();
    CpgCovArgs args = new CpgCovArgs();
    int value = 0;//bigwig文件的值
    int count = 0;//在treemap中计算每五个位点的和
    int count2 = 0;//treemap中全部位点的和
    int count3 = 0;//treemap中每去掉一个位点后剩余的个数
    //int count4 = 0;
    float percentage = 0;//cpg位点覆盖度
    List<Integer> cpgPosListInRegion = null;
    BigWigIterator iter = null;//
    WigItem wigItem = null;//bigwig文件迭代器
    public void parseCpgCovArgs(CpgCovArgs cpgCovArgs) throws Exception {
        args = cpgCovArgs;
        List<Region> bedRegionList = util.getBedRegionList(args.getBedPath());
        BBFileReader reader = new BBFileReader(args.getBigwig());
        BufferedWriter bufferedWriter = util.createOutputFile("", args.getTag() + ".txt");
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        //统计bigwig文件中相同值的个数
        for (Region region : bedRegionList) {
            cpgPosListInRegion = util.parseCpgFile(args.getCpgPath(), region);
            if (cpgPosListInRegion.size() < 1)
                continue;
            for (Integer cpgPos : cpgPosListInRegion) {
                iter = reader.getBigWigIterator(region.getChrom(), cpgPos - 1, region.getChrom(), cpgPos, true);
                while (iter.hasNext()) {
                    wigItem = iter.next();
                    value = (int) wigItem.getWigValue();
                    if (value == 0 ) continue;
                    map.put(value, map.getOrDefault(value, 0) + 1);
                    //count4++;

                }
            }
        }
        //bufferedWriter.write("总数："+count4);
        //bufferedWriter.write("\n"+String.valueOf(map)+"\n");
        //将map放入到treemap中，以5个位点为一组进行统计
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(1, map.get(1));
        int k = 0;
        int n = 1 , m = 1;
        int num = args.getRatio();
        for (int i : map.keySet()) {
                count += map.get(i);
                k++;
                if (k % num == 0) {
                    n=m*num;//使treemap中每个键按照指定的参数设定
                    m++;
                    count2 += count;
                    if (i==num&&i>1)//在第一个ratio区间内减去map[1]
                        count-= map.get(1);
                    treeMap.put(n, count);
                    count = 0;
                    k=0;
                }
        }

        count3 = count2;
        //bufferedWriter.write("\n"+String.valueOf(treeMap)+"\n");
        //计算覆盖度
        for (int i : treeMap.keySet()) {
            percentage = (float) count3 / (float) count2;

            count3 -= treeMap.get(i);

            bufferedWriter.write(i + "\t" + String.format("%.4f", percentage)+"\t" +count3+ "\n");
        }
    bufferedWriter.close();
    }

}