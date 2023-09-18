package com;


import com.entity.CpgCovArgs;
import com.utils.Annotation;
import org.apache.commons.cli.*;
import org.apache.commons.io.Charsets;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;

public class Main{
    CpgCov cpgCov = new CpgCov();

    public void main(String[] args) throws Exception {
        System.setProperty("java.awt.headless", "true");
        if (args != null && args[0] != null && !"".equals(args[0])) {
            if (args[0].equals("help") || args[0].equals("h") || args[0].equals("-help") || args[0].equals("-h")) {
                printHelp();
            }
            else if (args[0].equals("CpgCov")) {
                CpgCovArgs cpgCovArgs = parseCpgCovArgs(args);
                if (cpgCovArgs != null) {
                    cpgCov.parseCpgCovArgs(cpgCovArgs);
                }
            }
        }
    }
    private static Options getOptions(Field[] declaredFields) {
        Options options = new Options();
        Option helpOption = OptionBuilder.withLongOpt("help").withDescription("help").create("h");
        options.addOption(helpOption);
        Field[] fields = declaredFields;
        for(Field field : fields) {
            String annotation = field.getAnnotation(Annotation.class).value();
            Option option = null;
            if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
                option = OptionBuilder.withLongOpt(field.getName()).withDescription(annotation).create(field.getName());
            } else {
                option = OptionBuilder.withLongOpt(field.getName()).hasArg().withDescription(annotation).create(field.getName());
            }
            options.addOption(option);
        }
        return options;
    }
    private  void printHelp() {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, Charsets.UTF_8), true);
        pw.println("mHapQC: ");
        pw.println("Usage: java -jar mHapQC <command> [options]");
        pw.println("Commands:");

        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);
        pw.println("\tCpgCov");
    }
    private static CpgCovArgs parseCpgCovArgs(String[] args) throws ParseException {
        Options options = getOptions(CpgCovArgs.class.getDeclaredFields());
        BasicParser parser = new BasicParser();
        CpgCovArgs cpgCovArgs = new CpgCovArgs();
        CommandLine commandLine = parser.parse(options, args);
        if (commandLine.getOptions().length > 0) {
            if (commandLine.hasOption('h')) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.setWidth(110);
                helpFormatter.setSyntaxPrefix("");
                helpFormatter.printHelp("Options:", options);
                return null;
            } else {
                cpgCovArgs.setBigwig(commandLine.getOptionValue("bigwig"));
                cpgCovArgs.setCpgPath(commandLine.getOptionValue("cpgPath"));
                cpgCovArgs.setBedPath(commandLine.getOptionValue("bedPath"));
                cpgCovArgs.setTag(commandLine.getOptionValue("tag"));
            }
        } else {
            System.out.println("The paramter is null");
        }

        return cpgCovArgs;
    }
}