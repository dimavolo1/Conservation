import java.io.*;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        //Создать три экземпляра класса GameProgress
        GameProgress save1 = new GameProgress(99, 2, 10, 122.2);
        GameProgress save2 = new GameProgress(85, 5, 15, 156.8);
        GameProgress save3 = new GameProgress(75, 8, 21, 626.3);

        String pathToSavegamesFolder = "src/savegames";
        ArrayList<GameProgress> savesList = new ArrayList<>();
        ArrayList<String> pathToSavegameList = new ArrayList<>();

        savesList.add(save1);
        savesList.add(save2);
        savesList.add(save3);

        pathToSavegameList.add(pathToSavegamesFolder + "/save1.dat");
        pathToSavegameList.add(pathToSavegamesFolder + "/save2.dat");
        pathToSavegameList.add(pathToSavegamesFolder + "/save3.dat");

        saveGame(pathToSavegamesFolder, savesList);

        zipFiles(pathToSavegamesFolder, pathToSavegameList);
    }

    //Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
    static void saveGame(String path, ArrayList<GameProgress> savesList) {
        for (int i = 0; i < savesList.size(); i++) {
            try (FileOutputStream saveFos = new FileOutputStream(path + "/save" + (i + 1) + ".dat");
                 ObjectOutputStream saveOos = new ObjectOutputStream(saveFos)) {
                saveOos.writeObject(savesList.get(i));
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }

    //метод zipFiles(), принимающий в качестве аргументов String полный путь к файлу архива
    //и список запаковываемых объектов в виде списка строчек String полного пути к файлу
    static void zipFiles(String path, ArrayList<String> savesList) {
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(
                    new FileOutputStream(path + "/zipFile.zip"));
            for (int i = 0; i < savesList.size(); i++) {
                FileInputStream fileInputStream = new FileInputStream(savesList.get(i));

                zipOutputStream.putNextEntry(
                        new ZipEntry(savesList.get(i).substring(path.length() + 1))); // +1  символ '/'

                byte[] boofer = new byte[1024];
                fileInputStream.read(boofer);
                zipOutputStream.write(boofer);
                fileInputStream.close();
                zipOutputStream.flush();
            }
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            System.out.println("Create zipFile.zip - DONE");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

