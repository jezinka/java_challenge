package java_challenge

import static java_challenge.FileService.TEMP_DIRECTORY

class BootStrap {

    def init = { servletContext ->

        if (!new File(TEMP_DIRECTORY).exists()) {
            new File(TEMP_DIRECTORY).mkdir()
        }

    }
    def destroy = {
    }
}
