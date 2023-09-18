import com.Main;
import junit.framework.TestCase;
import org.junit.Test;

public class MainTest extends TestCase {
    @Test
    public void test_help() throws Exception {
        Main main = new Main();
        String arg0 = "help";
        String[] args = {arg0};
        main.main(args);
    }
    @Test
    public void test_ComputeCpgCov() throws Exception {
        Main main = new Main();
        String arg0 = "CpgCov";
        String arg1 = "-bigwig";
        String arg2 = "ERX202464_Cov.bw";
        String arg3 = "-cpgPath";
        String arg4 = "hg19_CpG.gz";
        String arg5 = "-bedPath";
        String arg6 = "hg19_cpgisland.bed";
        String arg7 = "-tag";
        String arg8 = "CpgCov.test";

        String[] args = {arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, };

        System.out.println("Work direqtory: " + System.getProperty("user.dir"));
        String argsStr = "";
        for (int i = 0; i < args.length; i++) {
            argsStr += args[i] + " ";
        }
        System.out.println(argsStr);

        main.main(args);
    }
}
