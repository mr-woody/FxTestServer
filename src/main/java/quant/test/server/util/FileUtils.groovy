package quant.test.server.util
import quant.test.server.command.Command
/**
 * Created by cz on 2017/3/6.
 */
class FileUtils {
    static final def TAG="FileUtils"
    /**
     * 拷贝脚本文件
     */
    static void copyResourceFileIfNotExists(File file,String path) {
//        if(!file.exists()){
            BufferedWriter writer=new BufferedWriter(new FileWriter(file))
            InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream(path);
            inputStream.withReader {
                it.readLines().each { writer.write(it+"\n") }
            }
            writer?.close()
            //修改shell脚本可执行权限
            Command.exec("chmod 777 $file.absolutePath")
//        }
    }
}
