import pkg.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Word{
    private String word;
    private ArrayList<ArrayList<Picture>> lines;
    private Path letterPath = Paths.get("pictures\\characters");
    private int gap;

    public Word(String startWord) {
        lines = new ArrayList<ArrayList<Picture>>();
        lines.add(new ArrayList<Picture>());
        word = startWord;
        gap = 8;
        char[] chars = word.toCharArray();
        int line = 0;
        for (int c = 0; c < chars.length; c++) {
            lines.get(line).add(new Picture(letterPath.toAbsolutePath() + "\\" + (int)(chars[c]) + ".png"));
            if (c > 0) {
                lines.get(line).get(lines.get(line).size() - 1).translate(lines.get(line).get(lines.get(line).size() - 2).getMaxX() + gap, 0);
                if (lines.get(line).get(lines.get(line).size() - 1).getMaxX() > 1280) {
                    Picture lastLetter = lines.get(line).remove(lines.get(line).size() - 1);
                    Picture scndLastLett = lines.get(line).remove(lines.get(line).size() - 1);
                    lines.get(line).add(new Picture(letterPath.toAbsolutePath() + "\\45.png"));
                    line++;
                    lines.get(line).add(scndLastLett);
                    lines.get(line).add(lastLetter);
 		    lines.get(line).get(0).translate(lines.get(line-1).get(0).getX() - lines.get(line).get(0).getX(), 43);
		    lines.get(line).get(1).translate(lines.get(line).get(0).getMaxX() + gap, 43);
                }
            }
        }
    }

    public void translate(int x, int y) {
	for (ArrayList<Picture> letters:lines) {
            for (Picture picture: letters) {
                picture.translate(x,y);
            }
	}
    }

    public void draw() {
	for (ArrayList<Picture> letters:lines) {
            for (Picture picture: letters) {
                picture.draw();
            }
	}
    }

    public void undraw() {
	for (ArrayList<Picture> letters:lines) {
            for (Picture picture: letters) {
                picture.undraw();
            }
	}
    }

    public void setGap(int newGap) {
        gap = newGap;
    }
}