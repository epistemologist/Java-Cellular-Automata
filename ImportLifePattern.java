import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Class that allows importing patterns from LifeWiki
 * Code by: Husnain Raza
 */
public class ImportLifePattern {
    public static final int BORDER = 200;

    // Utility functions for plaintext format
    public static String filterStringNum(String s) {
        String out = "";
        for (int i = 0; i < s.length(); i++) {
            if (48 <= s.charAt(i) && s.charAt(i) <= 57) {
                out += s.charAt(i);
            }
        }
        return out;
    }

    // algorithm + code structure inspired/gotten from https://rosettacode.org/wiki/Run-length_encoding#Java
    public static String rleDecode(String s) {
        StringBuffer out = new StringBuffer();
        Pattern p = Pattern.compile("[0-9]+|[a-z]");
        Matcher m = p.matcher(s);
        while (m.find()) {
            int n = Integer.parseInt(m.group());
            m.find();
            while (n-- != 0) {
                out.append(m.group());
            }
        }
        return out.toString();
    }

    public static int[][] extendArray(int[][] in, int l, int h) {
        int x = in.length;
        int y = in[0].length;
        if ((l-x)%2 != 0) {
            l++;
        }
        if ((h-y)%2 != 0) {
            h++;
        }
        int[][] out = new int[l+1][h+1];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                out[i + 1 + (l-x)/2][j + 1 + (h-y)/2] = in[i][j];
            }
        }
        return out;
    }

    public static ArrayList<String> getPattern(String patternURL) throws Exception {
        URL url = new URL(patternURL);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        ArrayList<String> pattern = new ArrayList<>();
        String line = "";
        while ((line = in.readLine()) != null) {
            //System.out.println(line);
            //System.out.println(line.length());
            if (line.length() == 0 || line.charAt(0) != '!') {
                pattern.add(line);
            }
        }
        in.close();
        return pattern;
    }

    public static ArrayList<String> formatPattern(String patternURL) throws Exception {
        ArrayList<String> pattern = getPattern(patternURL);
        int maxLength = 0;
        for (String s: pattern) {
            if (maxLength < s.length()) {
                maxLength = s.length();
            }
        }
        ArrayList<String> outPattern = new ArrayList<>();
        for (String s: pattern) {
            String t = s;
            while (t.length() < maxLength) {
                t += ".";
            }
            outPattern.add(t);
        }
        return outPattern;
    }

    public static ArrayList<String> boundingBox(String patternURL, int width, int height) throws Exception {
        ArrayList<String> pattern = formatPattern(patternURL);
        int rowLength = pattern.get(0).length();
        String s = "";
        for (int i = 0; i < rowLength; i++) {
            s += ".";
        }
        while (pattern.size() < height) {
            pattern.add(0,s);
            pattern.add(pattern.size(),s);
        }
        ArrayList<String> out = new ArrayList<>();
        for (String t:pattern) {
            while (t.length() < width) {
                t = "." + t + ".";
            }
            out.add(t);
        }
        return out;
    }

    public static ArrayList<String> getCellsPattern(String patternURL) throws Exception {
        int[] boundingBoxSize = guessBoundingBox(patternURL);
        return boundingBox(guessCellsURL(patternURL), boundingBoxSize[0], boundingBoxSize[1]);
    }

    public static int[] guessBoundingBox(String urlIn) throws Exception {
        URL url = new URL(urlIn);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        ArrayList<String> lines = new ArrayList<>();
        String line;
        int[] out = new int[2];
        int count = 0;
        while ((line = in.readLine()) != null) {
            //System.out.println(line);
            //System.out.println(line.length());
            lines.add(line);
        }
        in.close();
        for (String s:lines) {
            if (s.contains("&#215;") && s.contains("<td>")) {
                for (String t: s.split("&#215;")) {
                    out[count] = Integer.parseInt(filterStringNum(t));
                    count++;
                }
            }
        }
        return out;
    }

    public static String guessCellsURL(String urlIn) throws Exception {
        String pattern = "http:\\/\\/.*\\.cells\"";
        URL url = new URL(urlIn);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = in.readLine()) != null) {
            //System.out.println(line);
            //System.out.println(line.length());
            lines.add(line);
        }
        in.close();
        for (String s:lines) {
            if (s.contains("http://www.conwaylife.com/patterns/") && s.contains("cells")) {
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(s);
                if (m.find()) {
                    return m.group().replaceAll("\"","");
                }
            }
        }
        return "";
    }

    // utility functions for RLE format

    public static ArrayList<String> getPatternRLE(String patternURL) throws Exception {
        URL url = new URL(patternURL);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        ArrayList<String> pattern = new ArrayList<>();
        String line;
        while ((line = in.readLine()) != null) {
            //System.out.println(line);
            //System.out.println(line.length());
            if (line.length() == 0 || line.charAt(0) != '#') {
                pattern.add(line);
            }
        }
        in.close();
        return pattern;
    }

    public static int[][] getArrayFromRLE(String patternURL) throws Exception {
        System.out.println(patternURL);
        ArrayList<String> file = getPatternRLE(patternURL);
        String[] data = file.get(0).split(",");
        int x = Integer.parseInt(filterStringNum(data[0]));
        int y = Integer.parseInt(filterStringNum(data[1]));
        int[][] out = new int[y][x];
        String pattern = "";
        for (int i = 1; i < file.size(); i++) {
            pattern += file.get(i);
        }
        String[] rows = pattern.split("\\$");
        ArrayList<String> rowsFiltered = new ArrayList<>();
        for (String s: rows) {
            s = s.replaceAll("bo","b1o").replaceAll("ob","o1b");
            if (s.charAt(0) == 'b' || s.charAt(0) == 'o') {
                s = "1" + s;
            }
            rowsFiltered.add(rleDecode(s));
        }
        ArrayList<int[]> rowsInt = new ArrayList<>();
        for (String s: rowsFiltered) {
            int[] temp = new int[s.length()];
            for (int i = 0; i < s.length(); i++) {
                char currentChar = s.charAt(i);
                if (currentChar == 'o') {
                    temp[i] = 1;
                }
            }
            rowsInt.add(temp);
        }
        for (int i = 0; i < rowsInt.size(); i++) {
            for (int j = 0; j < rowsInt.get(i).length; j++) {
                out[i][j] = rowsInt.get(i)[j];
            }
        }
        return out;
    }

    public static String guessRLEURL(String urlIn) throws Exception {
        String pattern = "http:\\/\\/.*\\.rle\"";
        URL url = new URL(urlIn);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = in.readLine()) != null) {
            //System.out.println(line);
            //System.out.println(line.length());
            lines.add(line);
        }
        in.close();
        for (String s:lines) {
            // System.out.println(s);
            if (s.contains("http://www.conwaylife.com/patterns/") && s.contains("rle") && !s.contains("synth.rle")) {
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(s);
                if (m.find()) {
                    return m.group().replaceAll("\"","");
                }
            }
        }
        throw new Exception();
    }

    public static void print2DArr(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == 1) {
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static int[][] getPatternFromURL(String url) throws Exception {
        try {
            ArrayList<String> pattern = getCellsPattern(url);
            int[][] out = new int[pattern.size()][pattern.get(0).length()];
            for (int i = 0; i < pattern.size(); i++) {
                for (int j = 0; j < pattern.get(0).length(); j++) {
                    if (pattern.get(i).charAt(j) == 'O') {
                        out[i][j] = 1;
                    }
                }
            }
            //print2DArr(out);
            out = extendArray(out, out.length + BORDER, out[0].length + BORDER);
            return out;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("cells format failed! trying rle format...");
        }
        try {
            int[] boundingBoxSize = guessBoundingBox(url);
            int[][] pattern = getArrayFromRLE(guessRLEURL(url));
            //print2DArr(pattern);
            pattern = extendArray(pattern, boundingBoxSize[0]+BORDER, boundingBoxSize[1]+BORDER);
            //print2DArr(pattern);
            return pattern;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}