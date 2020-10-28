package com.athjx.commonutils;

/**
 * 参数校验异常
 *
 * @author 程世博
 * @date 2019-07-04
 */
public class ValidException extends RuntimeException {
    
    public ValidException() {
        
        super();
    }
    
    public ValidException(String message) {
        
        super(message);
    }
    
    public ValidException(String message, Throwable cause) {
        
        super(message, cause);
    }
    
    public ValidException(Throwable cause) {
        
        super(cause);
    }
    
    protected ValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * 抛出异常
     * @param message
     * @author 刘朋
     * <br/>date 2019-10-08
     */
    public static  void throwValidException(String message) {
        throw new ValidException(message);
    }
    
}
