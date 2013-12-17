package com.sutternow.captcha

/**
 * Created with IntelliJ IDEA.
 * User: mpayne
 * Date: 11/26/13
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
class AnswerInfo {
    String answer
    File filePath
    String encryptedName


    public AnswerInfo(String answer, File filePath) {
        this.answer = answer
        this.filePath = filePath
    }

    public AnswerInfo(String answer, File filePath, encryptedName) {
        this.answer = answer
        this.filePath = filePath
        this.encryptedName = encryptedName
    }
}
