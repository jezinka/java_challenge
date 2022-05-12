package java_challenge

import grails.gorm.transactions.Transactional

@Transactional
class FileService {

    public static String TEMP_DIRECTORY = "./temp"

    File getFileFromURL(String url, String filename) {
        File file = new File(TEMP_DIRECTORY + "/" + filename)
        FileOutputStream fos = new FileOutputStream(file)

        try {
            fos.write(new URL(url).getBytes())
        } catch (MalformedURLException e) {
            log.error("Invalid url", e)
            return null
        } finally {
            fos.close()
            file.deleteOnExit()
        }
        return file
    }
}