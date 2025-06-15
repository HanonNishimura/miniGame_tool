package org.example;

public class Game_result {
    public static int[] result(int[][] damage, int[][] max_mass, int[][] min_mass, int[][] theoreticalValue, String[] itemData, int[] result) {
        int totalError = 0;

        //2次元配列 (横2×縦3) のループ処理
        for (int x = 0; x < 3; x++) { //横のサイズ
            for (int y = 0; y < 2; y++) { //縦のサイズ
                int actual = damage[x][y];          //実際のダメージ
                int theoretical = theoreticalValue[x][y]; //理論値
                int maxLimit = max_mass[x][y];      //上限
                int minLimit = min_mass[x][y];      //下限
                int error; //誤差を格納する変数

                if (actual > maxLimit) {
                    //damage が max_mass を超えた場合
                    error = Math.abs(maxLimit - theoretical) + 20;
                } else if (actual < minLimit) {
                    //damage が min_mass を下回る場合
                    error = Math.abs(minLimit - theoretical) + 20;
                } else {
                    //通常の誤差
                    error = Math.abs(actual - theoretical);
                }

                totalError += error;
            }
        }

        //結果の出力
        if (totalError <= 7) {//できのよさ　星3
            result[0] = ++result[0];
            result[5] = 0;
        } else if (totalError <= 14) {
            result[1] = ++result[1];
            result[5] = 1;
        } else if (totalError <= 20) {
            result[2] = ++result[2];
            result[5] = 2;
        } else if (totalError == 21) {
            result[3] = ++result[3];
            result[5] = 3;
        } else {
            result[4] = ++result[4];
            result[5] = 4;
        }

        return result;
    }

    public static void gameSet(int[] result) { //結果の表示的な
        System.out.println("星3は" + result[0]);
        System.out.println("星2は" + result[1]);
        System.out.println("星1は" + result[2]);
        System.out.println("星0は" + result[3]);
        System.out.println("しっぱいは" + result[4]);
        int item = result[0] * 10 + result[1] * 3 + result[2] * 2 + result[3];
        System.out.println("超あまつゆの個数は" + item + "です。");
    }
}
