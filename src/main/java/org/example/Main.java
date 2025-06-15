package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

import static org.example.SKILL.getSkillInfoById;

public class Main {

    public static final int NAME_ID = 0; //アイテム識別ID
    public static final int name = 1; //アイテム名
    public static final int kinds = 2; //鍛冶型
    public static final int characteristics = 3; //特殊
    public static final int a_max = 4;
    public static final int b_max = 5;
    public static final int c_max = 6;
    public static final int d_max = 7;
    public static final int e_max = 8;
    public static final int f_max = 9;
    public static final int g_max = 10;
    public static final int h_max = 11;
    public static final int i_max = 12;
    public static final int a_min = 13;
    public static final int b_min = 14;
    public static final int c_min = 15;
    public static final int d_min = 16;
    public static final int e_min = 17;
    public static final int f_min = 18;
    public static final int g_min = 19;
    public static final int h_min = 20;
    public static final int i_min = 21;

    //鍛冶に必要な変数作成

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // NAME_IDを入力
        System.out.print("NAME_IDを入力してください: ");
        String item = scanner.nextLine();

        // アイテムデータを取得
        String[] itemData = LORD_SQLLITE.getItemByNameId(item);
        if (itemData != null) {
            System.out.println("取得したデータ:");
            System.out.println(Arrays.toString(itemData));
        } else {
            System.out.println("指定されたNAME_IDに該当するデータはありません。");
        }

        System.out.print("環境を選択してください: ");
        String character = scanner.nextLine();

        // アイテムデータを取得
        String[] characterData = LORD_SQLLITE.getItemByCharacterNameId(character);
        if (itemData != null) {
            System.out.println("取得したデータ:");
            System.out.println(Arrays.toString(characterData));
        } else {
            System.out.println("指定されたNAME_IDに該当するデータはありません。");
        }

        int playcount = 1; //回数の指定
        /*
        String item = "1"; //アイテム決め
        String character = "1";

        String[] itemData = LORD_SQLLITE.getItemByNameId(item);
        String[] characterData = LORD_SQLLITE.getItemByCharacterNameId(character);
　　　　　*/
        int[] result = new int[6]; //できのよさ
        for (int i = 0; i < playcount; i++) {
            System.out.println("回数 " + i);

            //理論値の生成、合計集中力、合計会心率、の生成を行う
            int totalConcentration;//合計集中力
            int[][] theoreticalValue = {
                    {0, 0},  // 1行目
                    {0, 0},  // 2行目
                    {0, 0}   // 3行目
            };
            double attentionRate; //会心率
            Random random = new Random();
            int max = a_max;
            int min = a_min;
            totalConcentration = Integer.parseInt(characterData[2]) + Integer.parseInt(characterData[5]); //集中力 + ハンマーの集中力
            attentionRate = Double.parseDouble(characterData[4]) + 0.6 + 1; //ハンマー会心率 + スキル会心率 + コツ(有)

            for (int cnt1 = 0; cnt1 < 3; cnt1++) { //理論値の作成
                for (int cnt2 = 0; cnt2 < 2; cnt2++) {
                    int randomNum = random.nextInt((Integer.parseInt(itemData[max])
                            - Integer.parseInt(itemData[min])) + 1) + Integer.parseInt(itemData[min]); //理論値の生成
                    max++;
                    min++;
                    theoreticalValue[cnt1][cnt2] = randomNum; //理論値の格納
                    //System.out.println(theoreticalValue[cnt1][cnt2]);
                }
            }

            switch (itemData[characteristics]) { //鍛冶の千金判定(今のところ倍半のみ)

                case "0":
                    result = Game.Game0(totalConcentration, theoreticalValue, attentionRate, itemData, result);
                    //特殊無し
                    break;
                case "1":
                    result = Game.Game0(totalConcentration, theoreticalValue, attentionRate, itemData, result);
                    //倍半千金

                    break;
                case "2":
                    //
                    break;
            }

        }

        Game_result.gameSet(result); //終了時の処理

    }
    //scanner.close(); //Scannerを閉じる
}
