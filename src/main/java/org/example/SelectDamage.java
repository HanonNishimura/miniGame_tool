package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectDamage {

    private static final String CSV_FILE_PATH = "src\\main\\resources\\温度.csv";

    public static int AttackDamage(int temperature, String magnification, int flag) {
        int damage = 0;
        List<Integer> possibleDamages = new ArrayList<>();

        //Stringに変換、ダブルコーテーションで囲む
        String temperatureStr = "\"" + temperature + "\""; // "1600"に変換

        double magValue = Double.parseDouble(magnification.trim());
        String magnificationStr = "\"" + ((magValue % 1 == 0) ? String.valueOf((int) magValue) : String.valueOf(magValue)) + "\"";

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            br.readLine(); //ヘッダースキップ

            boolean found = false; //行発見したかのフラグ

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 9) continue; // 不完全な行をスキップ

                //ダブルコーテーションを取り除く
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replace("\"", "").trim();
                }

                //temperature、magnification が一致する行を探す
                if (values[1].equals(temperatureStr.replace("\"", "")) && values[0].equals(magnificationStr.replace("\"", ""))) {
                    // 一致する行が見つかった場合、ランダムに選択するための値をリストに追加
                    for (int i = 2; i <= 8; i++) {
                        if (!values[i].isEmpty()) {
                            possibleDamages.add(Integer.parseInt(values[i])); // 値をリストに追加
                        }
                    }
                    //一致した行の情報を表示
                    found = true; // 行が見つかったフラグを立てる
                    break; // 一致する行が見つかったのでループを抜ける
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // possibleDamages からランダムな値を選ぶ
        if (!possibleDamages.isEmpty()) {
            Random random = new Random();
            damage = possibleDamages.get(random.nextInt(possibleDamages.size())); // ランダムに選択
            switch (flag) {
                case 1: //倍
                    damage = damage * 2;
                case 2: //半
                    damage = damage / 2;
            }
        }
        return damage; //最終的なダメージ値
    }

    public static void addDamage(int[][] damage, int[][] damage_result) {
        //damage_resultの行数と列数を取得
        int rows = Math.min(damage.length, damage_result.length);
        int cols = Math.min(damage[0].length, damage_result[0].length);

        //合計値処理
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                damage[i][j] += damage_result[i][j];
            }
        }
    }
}
