import com.song.utils.Md5Util;
import org.junit.jupiter.api.Test;

public class Md5Test {

    @Test
    public void testMd5() {
        String md5 = Md5Util.getMd5("123456");
        System.out.println(md5);
    }
}
