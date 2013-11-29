package com.sutternow.captcha

/**
 * Created with IntelliJ IDEA.
 * User: mpayne
 * Date: 10/1/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
class ImageManager {

    File workingDirectory
    List<String> imageNames
    boolean cacheImages = false
    String imageExtension= '.png'



    private FilenameFilter imageFileNameFilter = new FilenameFilter() {

        public boolean accept(File dir, String name) {
            String lowerCaseName = name.toLowerCase()
            if (lowerCaseName.endsWith('@2x.png'))
                return false
            else if (lowerCaseName.endsWith(imageExtension))
                return true
            /*else if (lowerCaseName.endsWith(".gif"))
                return true
            else if (lowerCaseName.endsWith(".png"))
                return true*/
            else
                return false
        }

    }

    public boolean isCacheImages() {
        return cacheImages
    }

    public void setCacheImages(boolean cacheImages) {
        this.cacheImages = cacheImages
    }

    public Map<String, String > getImageMap() {

        Map imageMap = [:]
        File directory = workingDirectory // new File(workingDirectory)
        if (directory.exists()) {
            File[] images = directory.listFiles(imageFileNameFilter)
            images.each { imageMap.put(it.name, it.path)}
            // List
        } else {
            imageNames = [:]
        }

        //java.util.Collections.shuffle(imageMap)  // randomMize these

        return imageMap
    }



    public List<String> getImageNames() {

        if (cacheImages != false || imageNames == null || imageNames.size() == 0)
            imageNames = new ArrayList()
        File directory = workingDirectory // new File(workingDirectory)

        if (directory.exists()) {
            imageNames = directory.list(imageFileNameFilter).toList()
            // List
        } else {
            imageNames = []
        }

        java.util.Collections.shuffle(imageNames)  // randomMize these

        return imageNames
    }

    public void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory
    }


}
