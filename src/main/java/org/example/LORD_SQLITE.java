package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LORD_SQLITE {

    //CSVファイルのパス
    private static final String CSV_FILE_PATH = "src\\main\\resources\\アイテム数値.csv";
    private static final String CSV_FILE_PATH_2 = "src\\main\\resources\\環境.csv";

    public static String[] getItemByNameId(String nameIdStr) {
        int nameId;

        //NAME_ID を整数に変換
        try {
            nameId = Integer.parseInt(nameIdStr);
        } catch (NumberFormatException e) {
            System.out.println("NAME_IDは数値で指定してください。");
            return null;
        }

        //データを検索するための配列
        String[] item = null;

        //CSVファイルを読み込み
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                //ヘッダーをスキップ
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                //カンマで分割
                String[] values = line.split(",");

                //NAME_IDが一致するかチェック
                if (values.length > 0 && Integer.parseInt(values[0]) == nameId) {
                    item = values;  //一致する行を取得
                    break;
                }
            }

            //データを出力
            if (item != null) {
            } else {
                System.out.println("指定されたNAME_IDに該当するデータはありません。");
            }

        } catch (IOException e) {
            System.out.println("CSVファイルの読み込みエラー：" + e.getMessage());
        }

        return item;
    }

    public static String[] getItemByCharacterNameId(String characterStr) {
        int characterId;

        //NAME_ID を整数
        try {
            characterId = Integer.parseInt(characterStr);
        } catch (NumberFormatException e) {
            System.out.println("NAME_IDは数値で指定してください。");
            return null;
        }

        //データを検索するための配列
        String[] character = null;

        //CSVファイルを読み込み
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH_2))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                //ヘッダーをスキップ
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                //カンマで分割
                String[] values = line.split(",");

                //NAME_IDが一致するかチェック
                if (values.length > 0 && Integer.parseInt(values[0]) == characterId) {
                    character = values;  //一致する行を取得
                    break;
                }
            }

            //データを出力
            if (character != null) {
            } else {
                System.out.println("指定されたNAME_IDに該当するデータはありません。");
            }

        } catch (IOException e) {
            System.out.println("CSVファイルの読み込みエラー：" + e.getMessage());
        }

        return character;
    }
}
