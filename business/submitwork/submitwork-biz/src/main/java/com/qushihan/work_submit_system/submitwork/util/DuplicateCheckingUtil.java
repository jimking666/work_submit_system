package com.qushihan.work_submit_system.submitwork.util;

import java.util.HashSet;
import java.util.Optional;

public class DuplicateCheckingUtil {

    private static final char MARK = '"';

    private static final char SLASH = '/';

    private static final char BACKSLASH = '\\';

    private static final char STAR = '*';

    private static final char NEWLINE = '\n';

    //引号
    private static final int TYPE_MARK = 1;

    //斜杠
    private static final int TYPE_SLASH = 2;

    //反斜杠
    private static final int TYPE_BACKSLASH = 3;

    //星号
    private static final int TYPE_STAR = 4;

    // 双斜杠类型的注释
    private static final int TYPE_DSLASH = 5;

    /*Java关键字*/
    private String keyWords = "abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do" +
            "|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface" +
            "|long|native|new|package|private|protected|public|return|strictfp|short|static|super|switch|synchronized" +
            "|this|throw|throws|transient|try|void|volatile|while";

    private HashSet<String> keyWordSet = new HashSet<String>();

    public DuplicateCheckingUtil() {
        String list[] = keyWords.split("\\|");
        for (String keyword : list) {
            keyWordSet.add(keyword);
        }
    }

    // 得到相似度
    public float getSimilarity(String code1, String code2) {
        // TODO Auto-generated method stub
        return (float) (1 - ld(code1, code2) * 1.0 / Math.max(code1.length(), code2.length()));
    }

    /**
     * 得到预处理后的代码
     * @param oldContent
     * @return
     */
    public String getPreProcessedCode(String oldContent) {
        if (!Optional.ofNullable(oldContent).isPresent()) {
            return "";
        }
        String code = "";
        code = delComments(oldContent);
        int pos1 = 0, pos2 = 0;
        int len = code.length();
        boolean isString = false;
        StringBuffer ret = new StringBuffer();
        while (pos1 < len) {
            pos2++;
            if (isString) {
                if (pos2 < len - 1) {
                    if (code.substring(pos2, pos2 + 1).equals("\"") && !code.subSequence(pos2 - 1, pos2).equals(
                            "\\")) {
                        isString = false;
                        ret.append(delVariables(code.substring(pos1, pos2 + 1)));
                        pos1 = pos2 + 1;
                    }
                } else {
                    break;
                }
            } else {
                if (pos2 < len - 1) {
                    if (code.substring(pos2, pos2 + 1).equals("\"")) {
                        isString = true;
                        ret.append(delVariables(code.substring(pos1, pos2)));
                        pos1 = pos2;
                    }
                } else {
                    ret.append(delVariables(code.substring(pos1, code.length())));
                    break;
                }
            }
        }
        code = ret.toString();
        //删除所有空格和换行
        code = code.replaceAll("\\s", "");
        return code;
    }

    private String delVariables(String code) {
        code = "   " + code + "  ";
        //System.out.println("!"+code);
        int pos1 = 0, pos2 = 0;
        int len = code.length();
        boolean isVariables = false;
        StringBuffer ret = new StringBuffer();
        while (pos1 < len) {
            pos2++;
            if (isVariables) {
                if (code.substring(pos2, pos2 + 2).replaceAll("[0-9a-zA-Z_][^a-zA-Z_]", "").equals("")) {
                    System.out.println("111code.substring(pos2, pos2 + 2) == " + code.substring(pos2, pos2 + 2));
                    System.out.println("111code.substring(pos2, pos2 + 2).replaceAll == " + code.substring(pos2,
                                                                                                         pos2 + 2).replaceAll("[0-9a-zA-Z_][^a-zA-Z_]",""));
                    System.out.println("111boolean == " + code.substring(pos2, pos2 + 2).replaceAll("[0-9a-zA-Z_][^a" +
                                                                                                           "-zA-Z_]", "").equals(""));
                    isVariables = false;
                    String vv = code.substring(pos1, pos2 + 1);
                    System.out.println("1111 put == " + code.substring(pos1, pos2 + 1));
                    System.out.println();
                    if (this.keyWordSet.contains(vv)) {
                        ret.append(vv);
                        //System.out.println("vv="+vv);
                    }
                    pos1 = pos2 + 1;
                }
            } else {
                if (code.substring(pos2, pos2 + 2).replaceAll("[^\\._a-zA-Z][_a-zA-Z]", "").equals("")) {
                    System.out.println("222code.substring(pos2, pos2 + 2) == " + code.substring(pos2, pos2 + 2));
                    System.out.println("222code.substring(pos2, pos2 + 2).replaceAll == " + code.substring(pos2,
                                                                                                        pos2 + 2).replaceAll("[^\\._a-zA-Z][_a-zA-Z]", ""));
                    System.out.println("222boolean == " + code.substring(pos2, pos2 + 2).replaceAll("[^\\._a-zA-Z][_a-zA-Z]", "").equals(""));
                    isVariables = true;
                    ret.append(code.substring(pos1, pos2 + 1));
                    System.out.println("2222 put == " + code.substring(pos1, pos2 + 1));
                    System.out.println();
                    //System.out.println(code.substring(pos1,pos2+1));
                    pos1 = pos2 + 1;
                }
            }
            if (pos2 == len - 2) {
                break;
            }
            System.out.println("pos1 ==" + pos1 + " ,pos2 == " + pos2 + " ,see:" + code.substring(pos1, pos2 + 1));

        }
        return ret.toString().trim();
    }

    /**
     * Levenshtein distance最先是由俄国科学家Vladimir Levenshtein在1965年发明，用他的名字命名。不会拼读，可以叫它edit distance（编辑距离）。
     * Levenshtein distance可以用来：
     * Spell checking(拼写检查)
     * Speech recognition(语句识别)
     * DNA analysis(DNA分析)
     * Plagiarism detection(抄袭检测)
     * LD用m*n的矩阵存储距离值。算法大概过程：
     * str1或str2的长度为0返回另一个字符串的长度。
     * 初始化(n+1)*(m+1)的矩阵d，并让第一行和列的值从0开始增长。
     * 扫描两字符串（n*m级的），如果：str1[i] == str2[j]，用temp记录它，为0。否则temp记为1。然后在矩阵d[i][j]赋于d[i-1][j]+1 、d[i][j-1]+1、d[i-1][j-1]+temp三者的最小值。
     * 扫描完后，返回矩阵的最后一个值即d[n][m]      * @param str1
     * @param str2
     * @return
     */
    private int ld(String str1, String str2) {
        //Distance
        int [][] d;
        int n = str1.length();
        int m = str2.length();
        int i; //iterate str1
        int j; //iterate str2
        char ch1; //str1
        char ch2; //str2
        int temp;
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) {
            ch1 = str1.charAt(i - 1);
            //match str2
            for (j = 1; j <= m; j++)
            {
                ch2 = str2.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                }
                else {
                    temp = 1;
                }
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    private int min(int one, int two, int three) {
        int min = one;
        if (two < min) {
            min = two;
        }
        if (three < min) {
            min = three;
        }
        return min;
    }

    /**
     * 删除char[]数组中start位置到end位置的元素
     *
     * @param target
     * @param start
     * @param end
     * @return
     */
    private char[] del(char[] target, int start, int end) {
        char[] tmp = new char[target.length - (end - start + 1)];
        System.arraycopy(target, 0, tmp, 0, start);
        System.arraycopy(target, end + 1, tmp, start, target.length - end
                - 1);
        return tmp;
    }

    /**
     * 删除代码中的注释
     *
     * @param target
     * @return
     */
    private String delComments(String target) {
        int preType = 0;
        int mark = -1, token = -1;
        // 输入字符串
        char[] input =  target.toCharArray();
        for (int cur = 0; cur < input.length; cur++) {
            if (input[cur] == MARK) {
                // 首先判断是否为转义引号
                if (preType == TYPE_BACKSLASH) {
                    continue;
                }
                // 已经进入引号之内
                if (mark > 0) {
                    // 引号结束
                    mark = -1;
                } else {
                    mark = cur;
                }
                preType = TYPE_MARK;
            } else if (input[cur] == SLASH) {
                // 当前位置处于引号之中
                if (mark > 0) {
                    continue;
                }
                // 如果前一位是*，则进行删除操作
                if (preType == TYPE_STAR) {
                    input = del(input, token, cur);
                    // 退回一个位置进行处理
                    cur = token - 1;
                    preType = 0;
                } else if (preType == TYPE_SLASH) {
                    token = cur - 1;
                    preType = TYPE_DSLASH;
                } else {
                    preType = TYPE_SLASH;
                }
            } else if (input[cur] == BACKSLASH) {
                preType = TYPE_BACKSLASH;
            } else if (input[cur] == STAR) {
                // 当前位置处于引号之中
                if (mark > 0) {
                    continue;
                }
                // 如果前一个位置是/,则记录注释开始的位置
                if (preType == TYPE_SLASH) {
                    token = cur - 1;
                }
                preType = TYPE_STAR;
            } else if(input[cur] == NEWLINE) {
                if(preType == TYPE_DSLASH)
                {
                    input = del(input, token, cur);
                    // 退回一个位置进行处理
                    cur = token - 1;
                    preType = 0;
                }
            }

        }
        return new String(input);
    }
}
