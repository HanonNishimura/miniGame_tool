package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class SKILL {

    private static final String CSV_FILE_PATH = "src\\main\\resources\\技.csv";

    public static List<SkillInfo> getSkillInfoById(String[] ids) {
        List<SkillInfo> skills = new ArrayList<>();

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

                //IDが一致するかチェック
                for (String id : ids) {
                    if (values.length > 0 && values[0].equals(id)) {
                        String skillName = values[2]; //skill_name
                        Integer consumption = values[3].isEmpty() ? null : Integer.parseInt(values[3]); //consumption
                        Double temperature = values[4].isEmpty() ? null : Double.parseDouble(values[4]); //temperature
                        skills.add(new SkillInfo(id, skillName, consumption, temperature)); //SkillInfoオブジェクトを追加
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("CSVファイルの読み込みエラー：" + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("数値変換エラー：" + e.getMessage());
        }

        return skills;
    }

    public static int[][] UseSkill(String selectChooseSkill, int temperature, String selectmass, int Concentration, int[][] theoreticalValue, double attentionRate, int[][] damage, int[][] max_mass, int[][] min_mass, int flag) { //スキルのダメージを反映する

        //スキル情報を取得 skillid が選ばれたもの
        String[][] massId = {
                {"A", "B"},  //1行目
                {"C", "D"},  //2行目
                {"E", "F"}   //3行目
        };
        int[][] damage_result = {
                {0, 0},  //1行目(A,B)
                {0, 0},  //2行目(C,D)
                {0, 0},  //3行目(E,F)
                {0, 0}   //ダメージ数、温度
        };
        List<SkillInfo> skills = getSkillInfoById(new String[]{selectChooseSkill});
        double tmpattentionRate;
        if (!skills.isEmpty()) {
            SkillInfo skill = skills.get(0);

            //情報を配列に格納
            String[] skillInfoArray = new String[4];
            skillInfoArray[0] = skill.getId(); //ID
            skillInfoArray[1] = skill.getSkillName(); //Skill Name
            skillInfoArray[2] = (skill.getConsumption() != null) ? skill.getConsumption().toString() : "N/A"; //Consumption
            skillInfoArray[3] = (skill.getTemperature() != null) ? skill.getTemperature().toString() : "N/A"; //Temperature

            //配列の内容を出力
            System.out.println("ID: " + skillInfoArray[0] +
                    ", Skill Name: " + skillInfoArray[1] +
                    ", Consumption: " + skillInfoArray[2] +
                    ", Temperature: " + skillInfoArray[3]);

            int[] mass = new int[2];
            mass = SKILL.SelectMass(selectmass);
            //スキルごとの計算
            switch (skillInfoArray[0]) {
                case "1": //たたく
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    break;


                case "2": //上下打ち
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    mass[0] = mass[0] + 1;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    break;


                case "3": //てかげん打ち
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    break;


                case "4": //2倍打ち
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    break;


                case "5": //火力上げ
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]); //集中力消費
                    damage_result[3][1] = temperature + 300;//温度追加
                    break;


                case "6": //4連打ち
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    mass[1] = mass[1] + 1;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    mass[1] = mass[1] - 1;
                    mass[0] = mass[0] + 1;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    mass[1] = mass[1] + 1;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    break;


                case "7": //みだれ打ち(実装予定なし)
                    System.out.println("未実装です");
                    break;

                case "8": //3倍打ち
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    break;


                case "9": //ねらい打ち
                    tmpattentionRate = attentionRate;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    attentionRate = tmpattentionRate; //会心率増価
                    break;


                case "10": //超4連打ち
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    mass[1] = mass[1] + 1;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    mass[1] = mass[1] - 1;
                    mass[0] = mass[0] + 1;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    mass[1] = mass[1] + 1;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    break;


                case "11": //冷やし込み
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]); //集中力消費
                    damage_result[3][1] = temperature - 300;//温度追加
                    break;


                case "12": //ななめ打ち
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    mass[0] = mass[0] + 1;
                    mass[1] = mass[1] + 1;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    break;


                case "13": //熱風おろし
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 250;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    break;


                case "14": //上下ねらい打ち
                    tmpattentionRate = attentionRate;
                    attentionRate = attentionRate * 7; //会心率増価
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    mass[0] = mass[0] + 1;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    attentionRate = tmpattentionRate; //会心率増価
                    break;


                case "15": //弱ねらい打ち
                    tmpattentionRate = attentionRate;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    attentionRate = tmpattentionRate; //会心率増価
                    break;


                case "16": //左右打ち
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    mass[1] = mass[1] + 1;
                    damage_result[mass[0]][mass[1]] = SelectDamage.AttackDamage(temperature, skillInfoArray[3], flag);
                    damage_result[mass[0]][mass[1]] = satisfaction(damage_result, mass, theoreticalValue, attentionRate, damage, max_mass, min_mass);//通常の会心チェック
                    System.out.println(massId[mass[0]][mass[1]] + "に" + damage_result[mass[0]][mass[1]] + "ダメージ！");
                    damage_result[3][1] = temperature - 50;
                    damage_result[3][0] = Integer.parseInt(skillInfoArray[2]);
                    damage_result[3][0] = Concentration - Integer.parseInt(skillInfoArray[2]);
                    break;
                default:
                    break;

            }

        } else {
            System.out.println("ID: " + selectChooseSkill + " に該当するスキルは見つかりませんでした。");
        }
        return damage_result;
    }

    public static boolean isConsumptionChoice(String selectChooseSkill, int Concentration) {
        Map<Integer, Integer> skillConsumptionMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            boolean isHeader = true;

            //スキル、消費集中力
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(","); //カンマで分割
                if (parts.length >= 4) { //id, skill_name, consumption が必要
                    try {
                        int id = Integer.parseInt(parts[0].trim()); //id
                        int consumptions = Integer.parseInt(parts[3].trim()); //consumption
                        skillConsumptionMap.put(id, consumptions); //消費集中力
                    } catch (NumberFormatException e) {
                        //System.err.println("数値変換エラー: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        //electChooseSkill を整数型に変換
        int selectedId = Integer.parseInt(selectChooseSkill);

        //選択された id の消費集中力の取得
        Integer consumption = skillConsumptionMap.get(selectedId);

        if (consumption == null) {
            System.out.println("スキルID " + selectedId + " が見つかりません。");
            return false;
        }

        //集中力計算
        if (consumption < Concentration) {
            return true;
        } else {
            return false;
        }
    }

    public static int[] SelectMass(String selectmass) {
        int[] mass = new int[2];
        switch (selectmass) {
            case "a":
                mass[0] = 0;
                mass[1] = 0;
                break;

            case "b":
                mass[0] = 0;
                mass[1] = 1;
                break;

            case "c":
                mass[0] = 1;
                mass[1] = 0;
                break;

            case "d":
                mass[0] = 1;
                mass[1] = 1;
                break;

            case "e":
                mass[0] = 2;
                mass[1] = 0;
                break;

            case "f":
                mass[0] = 2;
                mass[1] = 1;
                break;

            default:
                break;
        }
        return mass;
    }

    public static int satisfaction(int[][] damage_result, int[] mass, int[][] theoreticalValue, double attentionRate, int[][] damage, int[][] max_mass, int[][] min_mass) {

        int critical = damage_result[mass[0]][mass[1]] * 2;
        int tmp_damage = damage_result[mass[0]][mass[1]] + damage[mass[0]][mass[1]]; //想定ダメージ数
        int critcal_tmp_damage = critical + damage[mass[0]][mass[1]]; //会心時想定ダメージ数
        int tmp = theoreticalValue[mass[0]][mass[1]] - damage[mass[0]][mass[1]];

        if (damage[mass[0]][mass[1]] >= theoreticalValue[mass[0]][mass[1]]) { //まず現在のダメ数が理論値を超えているかどうか
            return damage_result[mass[0]][mass[1]]; //超える場合はスルー
        }
        //ランダムな数を生成
        Random random = new Random();
        double randomValue = random.nextDouble() * 100; //0.0から100.0の範囲の乱数を生成
        //確率判定
        //double randomValue = 0; //100%会心

        if (randomValue < attentionRate) { //会心の判定
            System.out.println("critical!");
            if (critcal_tmp_damage > theoreticalValue[mass[0]][mass[1]]) { //damage_resultが理論値以上か
                damage_result[mass[0]][mass[1]] = theoreticalValue[mass[0]][mass[1]] - damage[mass[0]][mass[1]]; //理論値までのダメ代入
            } else {
                damage_result[mass[0]][mass[1]] = critical;//二倍のダメを代入
            }
        }
        return damage_result[mass[0]][mass[1]]; //会心じゃない
    }
}
