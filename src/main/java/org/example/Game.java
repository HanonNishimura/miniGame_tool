package org.example;

import java.util.Objects;
import java.util.Scanner;

public class Game {

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

    public static int[] Game0(int totalConcentration, int[][] theoreticalValue, double attentionRate, String[] itemData, int[] result) {

        Scanner scanner = new Scanner(System.in);
        int temperature = 1600;
        int flag = 0;
        int[][] damage = {
                {0, 0},  // 1行目
                {0, 0},  // 2行目
                {0, 0}   // 3行目
        };//与えたダメージの保管
        int[][] max_mass = {
                {Integer.parseInt(itemData[a_max]), Integer.parseInt(itemData[b_max])},
                {Integer.parseInt(itemData[c_max]), Integer.parseInt(itemData[d_max])},
                {Integer.parseInt(itemData[e_max]), Integer.parseInt(itemData[f_max])}
        };
        int[][] min_mass = {
                {Integer.parseInt(itemData[a_min]), Integer.parseInt(itemData[b_min])},
                {Integer.parseInt(itemData[c_min]), Integer.parseInt(itemData[d_min])},
                {Integer.parseInt(itemData[e_min]), Integer.parseInt(itemData[f_min])}
        };
        int a = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                damage[i][j] = a; // 0を代入
            }
        }

        int tmpDamage;
        int isfast = 0;
        int turn_cnt = 1;
        System.out.println("開始");
        for (int Concentration = totalConcentration; Concentration > 5 && temperature > 0; ) { //集中力が5以下になったら終了
            int[][] damage_result = new int[2][4];
            System.out.println("現在値" + "    温度" + temperature);
            System.out.println("残り集中力" + Concentration);
            System.out.println("A" + itemData[a_min] + "~" + itemData[a_max] + " B" + itemData[b_min] + "~" + itemData[b_max]);
            System.out.println("dmg:" + damage[0][0] + "    dmg:" + damage[0][1]);
            System.out.println("C" + itemData[c_min] + "~" + itemData[c_max] + " D" + itemData[d_min] + "~" + itemData[d_max]);
            System.out.println("dmg:" + damage[1][0] + "    dmg:" + damage[1][1]);
            System.out.println("E" + itemData[e_min] + "~" + itemData[e_max] + " F" + itemData[f_min] + "~" + itemData[f_max]);
            System.out.println("dmg:" + damage[2][0] + "    dmg:" + damage[2][1]);
            flag = selectTempreature(temperature, flag, isfast); //倍半判定
            isfast = 1;
            System.out.println("技選択 1~16まで");
            turn_cnt++;
            //技の選択
            String selectChooseSkill;
            while (true) {
                selectChooseSkill = scanner.nextLine(); //1~17まで
                if (isValidSkillChoice(selectChooseSkill)) {
                    if (Objects.equals(selectChooseSkill, "17")) {
                        result = Game_result.result(damage, max_mass, min_mass, theoreticalValue, itemData, result);
                        System.out.println("終了します。");
                        return result;
                    }
                    if (SKILL.isConsumptionChoice(selectChooseSkill, Concentration)) {
                        break; // 有効な入力があればループを抜ける
                    } else {
                        System.out.println("無効な入力です。1から16の数字を入力してください。");
                    }
                }
            }

            //マスの選択
            String selectmass;

            while (true) {
                // 技に応じたマス選択範囲の案内
                if ("2".equals(selectChooseSkill) || "14".equals(selectChooseSkill)) {
                    System.out.println("どこに？ a~dまで");
                } else if ("6".equals(selectChooseSkill) || "10".equals(selectChooseSkill) || "12".equals(selectChooseSkill)) {
                    System.out.println("どこに？ a,bのみ");
                } else if ("16".equals(selectChooseSkill)) {
                    System.out.println("どこに？ a,c,eのみ");
                } else if ("5".equals(selectChooseSkill) || "11".equals(selectChooseSkill)) {
                    selectmass = "";
                    break;
                } else {
                    System.out.println("どこに？ a~fまで");
                }

                selectmass = scanner.nextLine();
                if (isValidMassChoice(selectmass, selectChooseSkill)) {//有効かどうか
                    break;
                } else {
                    selectmass = "";
                }
            }

            damage_result = SKILL.UseSkill(selectChooseSkill, temperature, selectmass, Concentration, theoreticalValue, attentionRate, damage, max_mass, min_mass, flag);
            temperature = damage_result[3][1];
            Concentration = damage_result[3][0];
            SelectDamage.addDamage(damage, damage_result);

        }
        result = Game_result.result(damage, max_mass, min_mass, theoreticalValue, itemData, result);
        System.out.println("温度が0度になった為終了します。");
        return result;
    }

    private static boolean isValidSkillChoice(String choice) {
        try {
            int skillNumber = Integer.parseInt(choice);
            return skillNumber >= 1 && skillNumber <= 17;
        } catch (NumberFormatException e) {
            return false; // 数字でない場合は無効
        }
    }

    // マスの選択がa~fであるか確認
    // マスの選択が指定の条件に合致しているか確認
    private static boolean isValidMassChoice(String mass, String skill) {
        if (mass.length() != 1) {
            return false;
        }
        switch (skill) {
            case "5":
            case "11": //温度変化の身をどうするか考える

            case "2":
            case "14":
                return "abcd".contains(mass);
            case "6":
            case "10":
            case "12":
                return "ac".contains(mass);
            case "16":
                return "ace".contains(mass);
            default:
                return "abcdef".contains(mass);
        }
    }

    //倍半判定
    private static int selectTempreature(int temperature, int flag, int isfast) {
        flag = 0;
        if (isfast == 0) { //初回の場合の倍半判定スルー用
            flag = 0;
        } else {
            if (temperature % 400 == 0) { //倍半、半減ターンか
                flag = 1;
            } else if (temperature % 200 == 0) { //倍ターンか
                flag = 2;
            }

            switch (flag) {
                case 1:
                    System.out.println("半減ターン！");
                    break;
                case 2:
                    System.out.println("倍ターン！");
                    break;
                case 0:
            }
        }
        return flag;
    }
}
