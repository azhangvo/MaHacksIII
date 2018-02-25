package facerecognition.javafaces;

//import Failed.FaceRecognition;
//import Failed.FaceRecError;
//import Failed.MatchResult;
import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import static org.opencv.videoio.Videoio.*;

public class MaHacksIII {

    public static BufferedImage Mat2BufferedImage(Mat m) {
// source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
// Fastest code
// The output can be assigned either to a BufferedImage or to an Image
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;

    }

    public static float comparePeeps(Image compare, Image current) {
        float probability = 0;

        Mat frame = new Mat();
        CascadeClassifier faceCascade = new CascadeClassifier();
        faceCascade.load("C:\\Users\\Arthur\\Documents\\NetBeansProjects\\OpenCVCam\\FaceDetection\\haarcascade_frontalface_alt.xml");
//        System.out.println("Frame Obtained");
//        System.out.println("Captured Frame Width "
//                + frame.width() + " Height " + frame.height());
        //Imgcodecs.imwrite("camera.jpg", frame);
        //displayImage(Mat2BufferedImage(frame));

        // detect faces
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(frame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
                new Size(), new Size());
        Rect[] facesArray = faces.toArray();
        for (int i = 0; i < facesArray.length; i++) {
            Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);
        }
        Image img2 = Mat2BufferedImage(frame);
        ImageIcon icon = new ImageIcon(img2);

        return probability;
    }

    /*
         public static void displayImage(Image img2)
{   
    
    //BufferedImage img=ImageIO.read(new File("/HelloOpenCV/lena.png"));
    ImageIcon icon=new ImageIcon(img2);
    JFrame frame=new JFrame();
    frame.setLayout(new FlowLayout());        
    frame.setSize(img2.getWidth(null)+50, img2.getHeight(null)+50);     
    JLabel lbl=new JLabel();
    lbl.setIcon(icon);
    frame.add(lbl);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    static BufferedImage one;
    static BufferedImage two;
    static Image gray;
    static boolean faceDetect;
    static boolean face2;
    static FaceRecognition model;
    static boolean viable = true;
    public static void main(String args[]) throws FaceRecError {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        CascadeClassifier faceCascade = new CascadeClassifier();

        faceCascade.load("C:\\Users\\Arthur\\Documents\\NetBeansProjects\\OpenCVCam\\FaceDetection\\haarcascade_frontalface_alt.xml");

        model = new FaceRecognition();
        VideoCapture camera = new VideoCapture(0);
        System.out.println("HAI");
        if (!camera.isOpened()) {
            System.out.println("Error");
        } else {
            System.out.println("HAI");
            Mat frame = new Mat();
            JFrame jframe = new JFrame();
            JLabel enterName = new JLabel("Enter Name:");
            JTextField name = new JTextField("");
            JButton button = new JButton("Add Face");
            JLabel resulting = new JLabel("Result");
            JPanel panel = new JPanel();
            panel.add(enterName);
            panel.add(name);
            name.setPreferredSize(new Dimension(200, 20));
            name.setSize(200, 20);
            panel.add(button);
            panel.add(resulting);
            button.addActionListener((ActionEvent e) -> {
                // display/center the jdialog when the button is pressed
                File dir = new File(System.getProperty("user.home") + "\\SecureWebCam");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                camera.read(frame);
                BufferedImage one = Mat2BufferedImage(frame);
//                camera.read(frame);
//                BufferedImage two = Mat2BufferedImage(frame);
//                for (int i = 0; i < two.getHeight(); i++) {
//                    for (int j = 0; j < two.getWidth(); j++) {
//                        float r = new Color(two.getRGB(j, i)).getRed();
//                        float g = new Color(two.getRGB(j, i)).getGreen();
//                        float b = new Color(two.getRGB(j, i)).getBlue();
//                        int grayScaled = (int) (r + g + b) / 3;
//                        two.setRGB(j, i, new Color(grayScaled, grayScaled, grayScaled).getRGB());
//                    }
//                }
                if (camera.read(frame)) {
//                    System.out.println("Frame Obtained");
//                    System.out.println("Captured Frame Width "
//                            + frame.width() + " Height " + frame.height());
                    //Imgcodecs.imwrite("camera.jpg", frame);
                    //displayImage(Mat2BufferedImage(frame));

                    // detect faces
                    MatOfRect faces = new MatOfRect();
                    faceCascade.detectMultiScale(frame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
                            new Size(), new Size());
                    Rect[] facesArray = faces.toArray();
                    Image img2 = Mat2BufferedImage(frame);
                    one = one.getSubimage((int) facesArray[0].tl().x, (int) facesArray[0].tl().y, 210, 210);
//                    two = two.getSubimage((int) facesArray[0].tl().x, (int) facesArray[0].tl().y, 210, 210);
//                    System.out.println("OK");
                    //break;
                }

                File dir1 = new File(System.getProperty("user.home") + "\\SecureWebCam\\" + name.getText() + ".jpg");
//                File dir2 = new File(System.getProperty("user.home") + "\\SecureWebCam\\" + name.getText() + "gray.jpg");
//                try {
////                    dir1.createNewFile();
////                    dir2.createNewFile();
//                } catch (IOException ex) {
//                    Logger.getLogger(MaHacksIII.class.getName()).log(Level.SEVERE, null, ex);
//                }
                try {
                    ImageIO.write(one, "jpg", dir1);
//                    ImageIO.write(two, "jpg", dir2);
                } catch (IOException ex) {
                    Logger.getLogger(MaHacksIII.class.getName()).log(Level.SEVERE, null, ex);
                }
                name.setText("");
            });
            jframe.getContentPane().add(panel);
            jframe.setLayout(new FlowLayout());
            JLabel lbl = new JLabel();

            while (viable) {
                if (camera.read(frame)) {
//                    System.out.println("Frame Obtained");
//                    System.out.println("Captured Frame Width "
//                            + frame.width() + " Height " + frame.height());
                    //Imgcodecs.imwrite("camera.jpg", frame);
                    //displayImage(Mat2BufferedImage(frame));

                    // detect faces
                    MatOfRect faces = new MatOfRect();
                    faceCascade.detectMultiScale(frame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
                            new Size(), new Size());
                    Rect[] facesArray = faces.toArray();
                    for (int i = 0; i < facesArray.length; i++) {
                        Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(255, 255, 255), 3);
                        face2 = false;
//                        System.out.println((facesArray[i].br().x-facesArray[i].tl().x)*(facesArray[i].tl().y-facesArray[i].br().y)+"  "+camera.get(CV_CAP_PROP_FRAME_WIDTH)*camera.get(CV_CAP_PROP_FRAME_HEIGHT)/5);
                        if (-1 * (facesArray[i].br().x - facesArray[i].tl().x) * (facesArray[i].tl().y - facesArray[i].br().y) > camera.get(CV_CAP_PROP_FRAME_WIDTH) * camera.get(CV_CAP_PROP_FRAME_HEIGHT) / 8) {
                            if (!faceDetect) {
                                faceDetect = true;
                                face2 = true;
                                System.out.println("1/8 covered");
                                File dir = new File(System.getProperty("user.home") + "\\SecureWebCam\\Original");
                                if (!dir.exists()) {
                                    dir.mkdir();
                                }
                                dir = new File(System.getProperty("user.home") + "\\SecureWebCam\\Original\\Cam.jpg");
                                camera.read(frame);
                                BufferedImage img = Mat2BufferedImage(frame);
                                if (facesArray[i].tl().x + 210 > camera.get(CV_CAP_PROP_FRAME_WIDTH) || facesArray[i].tl().y + 210 > camera.get(CV_CAP_PROP_FRAME_HEIGHT)) {

                                } else {
                                    img = img.getSubimage((int) facesArray[i].tl().x, (int) facesArray[i].tl().y, 210, 210);
                                }
                                try {
                                    ImageIO.write(img, "jpg", dir);
                                } catch (IOException ex) {
                                    Logger.getLogger(MaHacksIII.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                MatchResult result;
                                result = null;
                                try {
//                                    String[] arguments = new String[2];
//                                    arguments[0]=System.getProperty("user.home")+"\\SecureWebCam";
//                                    arguments[0]=System.getProperty("user.home")+"\\SecureWebCam\\Cam.jpg";
//                                    TestFaceRecognition.main(arguments);
                                    result = model.processSelections(dir.getPath(), System.getProperty("user.home") + "\\SecureWebCam", FaceRecognition.parseDirectory(System.getProperty("user.home") + "\\SecureWebCam", "jpg").size(), 5 + "");
                                } catch (FaceRecError ex) {
                                    Logger.getLogger(MaHacksIII.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                System.out.println(result.getMatchSuccess());
                                System.out.println(FaceRecognition.parseDirectory(System.getProperty("user.home") + "\\SecureWebCam", "jpg").size());
                                if (result != null && result.getMatchSuccess()) {
                                    System.out.println("Match!");
                                    resulting.setText("Match!");
                                } else {
                                    System.out.println("No match!");
                                    resulting.setText("Failed");
//                                }

                                }
                            } else {
                                if (!face2) {
                                    faceDetect = false;
                                }
                            }
                            System.out.println(faceDetect + " " + face2);
                        }
                        Image img2 = Mat2BufferedImage(frame);
                        ImageIcon icon = new ImageIcon(img2);
                        lbl.setIcon(icon);
                        jframe.setSize(img2.getWidth(null) + 50, img2.getHeight(null) + 50);

                        jframe.add(lbl);
                        jframe.setVisible(true);
                        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//                    System.out.println("OK");
                        //break;
                    }
                }
            }
            camera.release();
        }
    }
}