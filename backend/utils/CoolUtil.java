package NovaJaxe.backend.utils;

import NovaJaxe.Main;
import NovaJaxe.novahandlers.*;
import javafx.scene.media.AudioClip;
import org.json.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.sound.sampled.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

public class CoolUtil extends Main  {
    public static int SCROLL = 0;
    public static int CONFIRM = 1;
    public static int CANCEL = 2;

    public static Vector<Clip> clips = new Vector<>(0);

    public static void playMenuSong() {
        //System.out.println(music.getSource());
        if (!music.isRunning()) {
            music.setMicrosecondPosition(0);
            music.start();
        }
    }
    public static void playMusic(String path) {
        music.stop();
        music = getClip(path);
        music.start();
    }
    public static void playMusic(String path, double bpm) {
        music.stop();
        MusicBeatState.globalNextState.updateBPM(bpm);
        music = getClip(path);
        music.start();
    }

    public static AudioClip getSound(String path) {
        AudioClip sound = new AudioClip(Main.class.getResource(path).toExternalForm());
        //sound.setVolume(volume);
        //sound.play();
        return sound;
    }
    public static AudioClip getSound(String path, String folder) {
        AudioClip sound = new AudioClip(Main.class.getResource(folder + "/" + path).toExternalForm());
        //sound.setVolume(volume);
        //sound.play();
        return sound;
    }

    public static void setVolume(double volume) {
        for (Clip clip : clips) {
            //FloatControl volCtrl = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);
            //volCtrl.setValue((float) volume);

            Control control = clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (control != null && control instanceof FloatControl) {
                FloatControl gainControl = (FloatControl) control;
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            }
        }
    }
    public static Clip getClip(String path) throws RuntimeException {
        if (!path.endsWith(".wav"))
            path += ".wav";
        Clip sound = null;
        try {
            File file = new File(pathify(path));
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            sound = AudioSystem.getClip();
            sound.open(ais);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
        //sound.setVolume(volume);
        //sound.play();
        clips.add(sound);
        return sound;
    }
    public static AudioClip getAudioClip(String path) {
        // Create an AudioClip
        if (!path.endsWith(".wav")) {
            path += ".wav";
        }
        AudioClip audioClip = new AudioClip(Main.class.getResource(path).toExternalForm());
        return audioClip;
    }
    /*public static AudioClip getSound(String path, String folder) {
        AudioClip sound = new AudioClip(Main.class.getResource(folder + "/" + path).toExternalForm());
        sound.setVolume(volume);
        //sound.play();
        return sound;
    }*/

    public static AudioClip playSound(String path) {
        AudioClip sound = new AudioClip(Main.class.getResource(path).toExternalForm());
        sound.setVolume(volume);
        sound.play();
        return sound;
    }
    public static AudioClip playSound(String path, String folder) {
        AudioClip sound = new AudioClip(Main.class.getResource(folder + "/" + path).toExternalForm());
        sound.setVolume(volume);
        sound.play();
        return sound;
    }
    public static AudioClip playSound(String path, float daVolume) {
        AudioClip sound = new AudioClip(Main.class.getResource(path).toExternalForm());
        if (daVolume == 0) {
            sound.setVolume(0);
        } else {
            sound.setVolume((volume + daVolume)/2);
        }
        sound.play();
        return sound;
    }
    public static AudioClip playSound(String path, String folder, float daVolume) {
        AudioClip sound = new AudioClip(Main.class.getResource(folder + "/" + path).toExternalForm());
        if (daVolume == 0) {
            sound.setVolume(0);
        } else {
            sound.setVolume((volume + daVolume)/2);
        }
        sound.play();
        return sound;
    }

    public static void playMenuSFX(int sound) {
        switch (sound) {
            case 0:
                scrollMenu.stop();
                scrollMenu.setMicrosecondPosition(0);
                scrollMenu.start();
                break;
            case 1:
                confirm.stop();
                confirm.setMicrosecondPosition(0);
                confirm.start();
                break;
            case 2:
                cancel.stop();
                cancel.setMicrosecondPosition(0);
                cancel.start();
                break;
        }
    }

    public static String readFile(BufferedReader reader) throws IOException {
        String line;
        String text = "";
        while ((line = reader.readLine()) != null) {
            text += line + "\n";
        }
        return text;
    }

    public static JSONObject parseJson(String path) throws IOException {
        if (!path.endsWith(".json"))
            path += ".json";
        final String filepath = pathify(path);
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String text = readFile(reader);
        JSONObject jsonObj = new JSONObject(text);
        return jsonObj;
    }

    public static NovaKey[] addToArray(NovaKey arr[], NovaKey x)
    {
        int n = arr.length;
        int i;
        NovaKey[] newarr = new NovaKey[n + 1];
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];

        newarr[n] = x;

        return newarr;
    }

    public static String[] addToArray(String arr[], String x)
    {
        int n = arr.length;
        int i;
        String[] newarr = new String[n + 1];
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];

        newarr[n] = x;

        return newarr;
    }
    public static int[] addToArray(int arr[], int x)
    {
        int n = arr.length;
        int i;
        int[] newarr = new int[n + 1];
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];

        newarr[n] = x;

        return newarr;
    }
    public static double[] addToArray(double arr[], double x)
    {
        int n = arr.length;
        int i;
        double[] newarr = new double[n + 1];
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];

        newarr[n] = x;

        return newarr;
    }
    public static NovaSprite[] addToArray(NovaSprite arr[], NovaSprite x)
    {
        int n = arr.length;
        int i;
        NovaSprite[] newarr = new NovaSprite[n + 1];
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];

        newarr[n] = x;

        return newarr;
    }
    public static NovaAnimSprite[] addToArray(NovaAnimSprite arr[], NovaAnimSprite x)
    {
        int n = arr.length;
        int i;
        NovaAnimSprite[] newarr = new NovaAnimSprite[n + 1];
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];

        newarr[n] = x;

        return newarr;
    }

    public static Document parseXML(String path, String fallback) {
        final String xmlPath = pathify(path + ".xml");
        File xmlFile = new File(xmlPath);
        if (!xmlFile.exists()) {
            xmlFile = new File(pathify(fallback + ".xml"));
        }
        final DocumentBuilderFactory xmlBF = DocumentBuilderFactory.newInstance();
        final DocumentBuilder xmlDB;
        try {
            xmlDB = xmlBF.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        final Document daXML;
        try {
            daXML = xmlDB.parse(xmlFile);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        daXML.getDocumentElement().normalize();
        return daXML;
    }
    public static Document parseXML(String path) {
        try {
            if (!path.endsWith(".xml"))
                path += ".xml";
            final String xmlPath = pathify(path);
            File xmlFile = new File(xmlPath);
            if (!xmlFile.exists()) {
                trace("Could not find: " + path + ".xml");
                return null;
            }
            final DocumentBuilderFactory xmlBF = DocumentBuilderFactory.newInstance();
            final DocumentBuilder xmlDB;
            xmlDB = xmlBF.newDocumentBuilder();

            final Document daXML;
            daXML = xmlDB.parse(xmlFile);
            daXML.getDocumentElement().normalize();
            return daXML;
        } catch (ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkFileExists(String path) {
        String filePath = pathify(path);
        return new File(filePath).exists();
    }

    public static Element getXMLAttribute(Document xml, String name) {
        NodeList file = xml.getElementsByTagName(name);
        return (Element) file.item(0);
    }

    public static String formattedString(String string) {
        for (Object field : ConsoleColors.class.getFields()) {
            String[] daFieldArray = field.toString().split("\\.");
            String daColor = daFieldArray[daFieldArray.length-1];
            try {
                // With Trailing "Space"
                string = string.replace("$" + daColor.toLowerCase() + " ", "" + ConsoleColors.class.getField(daColor).get(null));
                string = string.replace("$" + daColor + " ", "" + ConsoleColors.class.getField(daColor).get(null));

                // Without Trailing "Space"
                string = string.replace("$" + daColor.toLowerCase(), "" + ConsoleColors.class.getField(daColor).get(null));
                string = string.replace("$" + daColor, "" + ConsoleColors.class.getField(daColor).get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        return string;
    }

    public static void trace(Object out) {
        if (!enableDebugTraces) return;
        if (out.getClass() == String.class) {
            String daOut = (String) out;
            daOut = formattedString(daOut);
            System.out.println(daOut);
        } else {
            System.out.println(out);
        }
    }

    public static String getClassPath(Object obj) {
        return obj.toString().replace("class ", "");
    }

    public static String getClassName(Object obj) {
        String[] classParts = obj.toString().split("\\.");
        String[] classParts2 = classParts[classParts.length-1].split("@");
        return classParts2[0];
    }

    public static String inputStreamReaderToString(InputStreamReader reader) { // Thank you geeks for geeks
        int t;
        String read_reslt="";

        // Use of read() method
        while(true)
        {
            try {
                if (!((t = reader.read()) != -1)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            read_reslt = read_reslt+(char)t;
        }
        return read_reslt;
    }

    public static int randomInt(int startRange, int endRange) {
        Random random = new Random();
        return random.nextInt(startRange, endRange);
    }

    public float randomFloat(float startRange, float endRange) {
        Random random = new Random();
        return random.nextFloat(startRange, endRange);
    }

    public static String[] listFilesInDirectory(String path) {
        final File folder = new File(path);
        String[] files = {};
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isDirectory()) {
                files = addToArray(files, fileEntry.getName());
            }
        }
        //trace(files);
        return files;
    }
    public static String[] listFilesInDirectory(String path, String suffix) {
        final File folder = new File(pathify(path));
        String[] files = {};
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isDirectory() && fileEntry.getName().endsWith(suffix)) {
                files = addToArray(files, fileEntry.getName());
            }
        }
        //trace(files);
        return files;
    }

    public static long getWAVduration(String filePath) {
        try {
            File file = new File(pathify(filePath));
            AudioFileFormat audioFileFormat = null;
            audioFileFormat = AudioSystem.getAudioFileFormat(file);
            AudioFormat format = audioFileFormat.getFormat();
            long frameLength = audioFileFormat.getFrameLength();
            float frameRate = format.getFrameRate();
            return (long) (frameLength / frameRate * 1000);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
