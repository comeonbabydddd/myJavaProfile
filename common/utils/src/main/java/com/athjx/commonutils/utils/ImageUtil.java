//package com.athjx.commonutils.utils;
//
//
//import javax.imageio.IIOImage;
//import javax.imageio.ImageIO;
//import javax.imageio.ImageWriteParam;
//import javax.imageio.ImageWriter;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.awt.image.ColorModel;
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.util.List;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
///**
// * 图片处理工具
// */
//public class ImageUtil {
//
//    /**
//     * 压缩图片（通过降低图片质量，保持原图大小）
//     *
//     * @param imgByte 文件字节
//     * @param quality 图片质量（0-1）
//     * @return byte[] 压缩后的图片（jpg）
//     */
//    public static byte[] compressPicByQuality(byte[] imgByte, float quality) {
//        byte[] imgBytes = null;
//        try {
//            ByteArrayInputStream byteInput = new ByteArrayInputStream(imgByte);
//            //            BufferedImage image = ImageIO.read(byteInput);容易出现变红的问题
//            Image imageTookit = Toolkit.getDefaultToolkit().createImage(imgByte);
//            BufferedImage image = toBufferedImage(imageTookit);
//
//            // 如果图片空，返回空
//            if (image == null) {
//                return null;
//            }
//            // 得到指定Format图片的writer（迭代器）
//            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
//            // 得到writer
//            ImageWriter writer = (ImageWriter) iter.next();
//            // 得到指定writer的输出参数设置(ImageWriteParam )
//            ImageWriteParam iwp = writer.getDefaultWriteParam();
//            // 设置可否压缩
//            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//            // 设置压缩质量参数
//            iwp.setCompressionQuality(quality);
//
//            iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
//
//            ColorModel colorModel = ColorModel.getRGBdefault();
//            // 指定压缩时使用的色彩模式
//            iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
//                    colorModel.createCompatibleSampleModel(16,
//                            16)));
//
//            // 开始打包图片，写入byte[]
//            // 取得内存输出流
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            IIOImage iIamge = new IIOImage(image, null, null);
//
//            // 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput
//            // 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput
//            writer.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));
//            writer.write(null, iIamge, iwp);
//            imgBytes = byteArrayOutputStream.toByteArray();
//        } catch (IOException e) {
//            System.out.println("write errro");
//            e.printStackTrace();
//        }
//        return imgBytes;
//    }
//
//    public static BufferedImage toBufferedImage(Image image) {
//        if (image instanceof BufferedImage) {
//            return (BufferedImage) image;
//        }
//        // This code ensures that all the pixels in the image are loaded
//        image = new ImageIcon(image).getImage();
//        BufferedImage bimage = null;
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        try {
//            int transparency = Transparency.OPAQUE;
//            GraphicsDevice gs = ge.getDefaultScreenDevice();
//            GraphicsConfiguration gc = gs.getDefaultConfiguration();
//            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
//        } catch (HeadlessException e) {
//            // The system does not have a screen
//        }
//        if (bimage == null) {
//            // Create a buffered image using the default color model
//            int type = BufferedImage.TYPE_INT_RGB;
//            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
//        }
//        // Copy image to buffered image
//        Graphics g = bimage.createGraphics();
//        // Paint the image onto the buffered image
//        g.drawImage(image, 0, 0, null);
//        g.dispose();
//        return bimage;
//    }
//
//
//    /**
//     * <p>Title: thumbnailImage</p>
//     * <p>Description: 根据图片路径生成缩略图 </p>
//     *
//     * @param imgFile    原图片路径
//     * @param w            缩略图宽
//     * @param h            缩略图高
//     * @param prevfix    生成缩略图的前缀
//     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
//     */
//
//    private static String DEFAULT_PREVFIX = "thumb_";
//    private static Boolean DEFAULT_FORCE = false;
//    //    @Value("${file.path}")
//
//    /**
//     * 获取内容中的第一张图片(不包含表情图)
//     *
//     * @param conent
//     * @return String
//     */
//    public static String getpicurl(String conent) throws IOException {
//        String picurl = "";
//        try {
//            if (conent != null && !conent.equals("")) {
//                try {
//                    if (conent != null && !conent.equals("")) {
////                        Pattern p = Pattern.compile("<img[^>]*>");
////                        Matcher m = p.matcher(conent);
//                        Matcher m = Pattern.compile("(?i)(?<=image/)[^\\\"]*(?=\\\")").matcher(conent);
//                        while (m.find()) {
//                            //本例中表情图链接中存在images/biaoqing链接
//                            if (m.group().contains("images/biaoqing")) {
//                                continue;
//                            } else {
//                                picurl = m.group();
////                                        .replaceAll("<img.*src=\"", "");
////                                        .replaceAll("([^\"]*)(.*)", "$2")
////                                        .replace("\"", "");
//                                if (!picurl.contains("file://")) {
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return picurl;
//    }
//
//    /**
//     * 64转图片
//     *
//     * @param picurl
//     * @return Path
//     */
//    public static Path toBase64(String picurl,String filePathPrefix) throws IOException {
//
//
//        final Base64 base64 = new Base64();
//        final byte[] textByte = picurl.substring(picurl.indexOf(",") + 1).getBytes("UTF-8");
//        try {
//            long uuid = GuuidUtil.getUUID();
//            File file = new File(filePathPrefix);
//            if (!file.exists()) {
//                file.mkdirs();//创建文件夹
//            }
//            return Files.write(Paths.get(filePathPrefix + uuid + "." + picurl.substring(0, picurl.indexOf(";"))),
//                    base64.decode(textByte), StandardOpenOption.CREATE);
//        } catch (IOException e) {
//            throw e;
//        }
//
//    }
//
//    /**
//     * <p>Title: thumbnailImage</p>
//     * <p>Description: 根据图片路径生成缩略图 </p>
//     *
//     * @param imgFile  原图片路径
//     * @param savePath 存储服务器路径
//     * @param w        缩略图宽
//     * @param h        缩略图高
//     * @param prevfix  生成缩略图的前缀
//     * @param force    是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
//     */
//    public static String thumbnailImage(String savePath, File imgFile, int w, int h, String prevfix, boolean force) {
//        try {
//            // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
//            String types = Arrays.toString(ImageIO.getReaderFormatNames());
//            String suffix = null;
//            // 获取图片后缀
//            if (imgFile.getName().indexOf(".") > -1) {
//                suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
//            }// 类型和图片后缀全部小写，然后判断后缀是否合法
//            if (suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()) < 0) {
//                return null;
//            }
//            Image img = ImageIO.read(imgFile);
//            if (!force) {
//                // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
//                int width = img.getWidth(null);
//                int height = img.getHeight(null);
//                if ((width * 1.0) / w < (height * 1.0) / h) {
//                    if (width > w) {
//                        h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w / (width * 1.0)));
//                    }
//                } else {
//                    if (height > h) {
//                        w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h / (height * 1.0)));
//                    }
//                }
//            }
//            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//            Graphics g = bi.getGraphics();
//            g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
//            g.dispose();
//            // 将图片保存在原目录并加上前缀
//            File file = new File(savePath);
//            if (!file.exists()) {
//                file.mkdirs();//创建文件夹
//            }
//            File writeFile = new File(file.getPath() + FILE_SEPARATOR + prevfix + imgFile.getName());
//            ImageIO.write(bi, suffix, writeFile);
//            File myDelFile = null;
//            myDelFile = new File(imgFile.getPath().toString());
//            myDelFile.delete();
//
//            return writeFile.getPath();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static String thumbnailImage(String savePath, String imagePath, int w, int h, String prevfix, boolean force) {
//        File imgFile = new File(imagePath);
//        return thumbnailImage(savePath, imgFile, w, h, prevfix, force);
//    }
//
//    public static String thumbnailImage(String savePath, String imagePath, int w, int h, boolean force) {
//        return thumbnailImage(savePath, imagePath, w, h, DEFAULT_PREVFIX, force);
//    }
//
//    public static String thumbnailImage(String savePath, String imagePath, int w, int h) {
//        return thumbnailImage(savePath, imagePath, w, h, DEFAULT_FORCE);
//    }
//
//
//    /**
//     * 获取内容中的网络图片
//     *
//     * @param conent
//     * @return List<String>
//     */
//
//    public static List<String> getHttpImage(String conent) throws IOException {
//        String picurl = "";
//        List<String> arrayList = new ArrayList<>();
//        try {
//            if (conent != null && !conent.equals("")) {
//                try {
//                    if (conent != null && !conent.equals("")) {
////                        Pattern p = Pattern.compile("<img.*src\\s*=\\s*(.*?)[^>]*?>");
////                        Matcher m = p.matcher(conent);
//                        Matcher m = Pattern.compile("src=\"http?(.*?)(\"|>|\\s+)").matcher(conent);
//                        while (m.find()) {
//                            //本例中表情图链接中存在images/biaoqing链接
//                            if (m.group().contains("images/biaoqing")) {
//                                continue;
//                            } else {
//                                picurl = m.group().replaceAll("<img.*src=\"", "").replaceAll("([^\"]*)(.*)", "$2");
//                                picurl = picurl.replaceAll("\"", "");
//                                arrayList.add(picurl);
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return arrayList;
//    }
//
//    /**
//     * 网络图片转base64
//     *
//     * @param imageUrl
//     * @return Map<String, String>
//     */
//    public static Map<String, String> downLoadImageToBase64(List<String> imageUrl) throws Exception {
//        Map<String, String> map = new HashMap<>();
//        for (String path : imageUrl) {
//            String remark = path.replace("https", "http");
//            InputStream inputStream = null;
//            ByteArrayOutputStream outputStream = null;
//            byte[] buffer = null;
//            try {
//                // 创建URL
//                URL url = new URL(remark);
//                // 创建链接
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//                conn.setRequestMethod("GET");
//                conn.setConnectTimeout(100000);
//                inputStream = conn.getInputStream();
//                outputStream = new ByteArrayOutputStream();
//                // 将内容读取内存中
//                buffer = new byte[1024];
//                int len = -1;
//                while ((len = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, len);
//                }
//                buffer = outputStream.toByteArray();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (inputStream != null) {
//                    try {
//                        // 关闭inputStream流
//                        inputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (outputStream != null) {
//                    try {
//                        // 关闭outputStream流
//                        outputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            // 对字节数组Base64编码
//            String encode = org.apache.commons.codec.binary.Base64.encodeBase64String(buffer);
////            String encode = new BASE64Encoder().encode(buffer);
//            //截取图片后缀
//            String substring = path.substring(path.length() - 5, path.length());
//            String suffix = substring.substring(substring.indexOf(".") + 1, substring.length());
//
//            encode = ("data:image/" + suffix + ";base64,") + encode;
//            map.put(path, encode);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//        }
//
//        return map;
//    }
//
//}
