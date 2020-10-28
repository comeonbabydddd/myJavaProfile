//package com.athjx.commonutils.utils;
//
//import com.adc.da.util.exception.AdcDaBaseException;
//import com.alibaba.excel.ExcelReader;
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.event.AnalysisEventListener;
//import com.alibaba.excel.exception.ExcelDataConvertException;
//import com.alibaba.excel.metadata.BaseRowModel;
//import com.alibaba.excel.metadata.Sheet;
//import com.alibaba.excel.support.ExcelTypeEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.EmptyFileException;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
//import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PushbackInputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @ClassName EasyExcelUtil
// * @Description easyExcel操作excel工具类
// * @Version 1.0
// */
//@Slf4j
//public class EasyExcelUtil {
//    /**
//     * @param in            文件输入流
//     * @param customContent 自定义模型可以在
//     *                      AnalysisContext中获取用于监听者回调使用
//     * @param eventListener 用户监听
//     * @throws IOException
//     * @throws EmptyFileException
//     * @throws InvalidFormatException
//     */
//    public static ExcelReader getExcelReader(InputStream in, Object customContent, AnalysisEventListener<?> eventListener) throws EmptyFileException, IOException, InvalidFormatException {
//        // 如果输入流不支持mark/reset，需要对其进行包裹
//        if (!in.markSupported()) {
//            in = new PushbackInputStream(in, 8);
//        }
//        // 确保至少有一些数据
//        byte[] header8 = IOUtils.peekFirst8Bytes(in);
//        ExcelTypeEnum excelTypeEnum = null;
//        if (NPOIFSFileSystem.hasPOIFSHeader(header8)) {
//            excelTypeEnum = ExcelTypeEnum.XLS;
//        }
//        if (DocumentFactoryHelper.hasOOXMLHeader(in)) {
//            excelTypeEnum = ExcelTypeEnum.XLSX;
//        }
//        if (excelTypeEnum != null) {
//            return new ExcelReader(in, excelTypeEnum, customContent, eventListener);
//        }
//        throw new InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
//
//    }
//
//    /**
//     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
//     */
//    private static final int BATCH_COUNT = 5;
//
//    /*
//     *@Description: 读取Excel文件内容
//     *@param in excel文件流
//     *@param tClass 对应excel实体bean
//     *@return: 对应excel实体bean的list
//     */
//    public static <T> List<T> getExcelContent(InputStream in, Class<T> tClass) throws IOException, InvalidFormatException {
//        List<T> excelPropertyIndexModelList = new ArrayList<>();
//        try {
//            AnalysisEventListener<T> listener = new AnalysisEventListener<T>() {
//
//                @Override
//                public void invoke(T t, AnalysisContext analysisContext) {
//                    excelPropertyIndexModelList.add(t);
//                    saveData(excelPropertyIndexModelList);
//                }
//
//                @Override
//                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//
//                }
//
//                @Override
//                public void onException(Exception exception, AnalysisContext context) throws Exception {
//                    ExcelDataConvertException excelDataConvertException = null;
//                    if (exception instanceof ExcelDataConvertException) {
//                        excelDataConvertException = (ExcelDataConvertException) exception;
//                    }
//                    int row = excelDataConvertException.getRowIndex() + 1;
//                    int column = excelDataConvertException.getColumnIndex() + 1;
//                    throw new AdcDaBaseException("第" + row + "行，第" + column + "列格式错误");
//                }
//            };
//            ExcelReader excelReader = EasyExcelUtil.getExcelReader(in, null, listener);
//            // 第二个参数为表头行数，按照实际设置
//            excelReader.read(new Sheet(1, 1, (Class<? extends BaseRowModel>) tClass));
//        } catch (Exception e) {
//            throw e;
//        }
//        return excelPropertyIndexModelList;
//    }
//
//    /**
//     * 加上存储数据库
//     */
//    private static <T> List<T> saveData(List<T> excelPropertyIndexModelList) {
//        return excelPropertyIndexModelList;
//    }
//
//
//}