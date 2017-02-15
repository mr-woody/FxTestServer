package quant.test.server.command

import groovy.transform.Canonical

/**
 * Created by cz on 2017/2/4.
 */
class Command {

    static def exec(String command){
        Result result=new Result()
        try{
            def process=command?.execute()
            process.inputStream.withReader {reader->reader.readLines().each {result.out<<it}}
            process.errorStream.withReader {reader->reader.readLines().each {result.error<<it}}
            process.waitFor()
            result.exit=process.exitValue()
            process.destroy()
        } catch (e){
            e.printStackTrace()
        }
        result
    }

    static def shell(shell,String...params){
        Result result=new Result()
        try {
            params?.each{ shell += (" " + it.toString().replaceAll("\\s+", "_")) }
            def processBuilder=new ProcessBuilder(["/bin/sh", "-c", shell])
            def env=processBuilder.environment()
            env+=(System.getenv())
            def process = processBuilder.start()
            process.inputStream.withReader {reader->reader.readLines().each {result.out<<it}}
            process.errorStream.withReader {reader->reader.readLines().each {result.error<<it}}
            process.waitFor()
            result.exit=process.exitValue()
            process.destroy()
        } catch (Exception e) {
            e.printStackTrace();
        }
        result
    }

    static def shell(shell,String[] params,closure){
        int exitValue=-1
        try {
            params?.each{ shell += (" " + it.toString().replaceAll("\\s+", "_")) }
            def processBuilder=new ProcessBuilder(["/bin/sh", "-c", shell as String])
            def env=processBuilder.environment()
            env+=(System.getenv())
            def process = processBuilder.start()
            process.inputStream.withReader {reader->reader.readLines().each {closure(it)}}
            process.waitFor()
            exitValue=process.exitValue()
            process.destroy()
        } catch (Exception e) {
            e.printStackTrace();
        }
        exitValue
    }

    @Canonical
    static class Result{
        def out
        def error
        def exit

        Result() {
            out=new StringBuilder()
            error=new StringBuilder()
            exit=-1
        }

        int out2Int(){
            out.isNumber()?out.toInteger():-1
        }

        def out2List(regex){
            out.toString().split(regex)
        }

        def outMatcher(regex,index=1){
            def matcher=out.toString()=~regex
            matcher?(matcher[0][index]):null
        }

        def errorMatcher(regex,index=1){
            def matcher=error.toString()=~regex
            matcher?(matcher[0][index]):null
        }

    }


}
