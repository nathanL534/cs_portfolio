// CS4: Project 3 LetterBoxed, Test program with 16 tests that report pass/fail
// version 1.0

// You don't need to change this file.
// Note half the tests (8/16) should pass in the starter project, because the
// starter project treats all solutions as invalid and all puzzles as unsolvable,
// and those tests involve invalid solutions and an unsolvable puzzle.

import java.util.*;
import java.io.*;

public class Test {
    private static LetterBoxed lb;
    private static int testsRun;
    private static int testsPassed;
    private static String[] sidesNYT20240323 = {"RYW", "PHU", "IMA", "GNC"};
    private static HashSet<String> emptyExclude = new HashSet<>();

    private static ArrayList<String> toList(Iterable<String> iterable) {
        if (iterable == null) {
            return null;
        }
        ArrayList<String> ret = new ArrayList<>();
        for (String s : iterable) {
            ret.add(s);
        }
        return ret;
    }

    private static void testCheckSolution(String testName, boolean expected, String[] sides, HashSet<String> exclude, Iterable<String> solution) {
        testsRun++;
        try {
            boolean actual = lb.checkSolution(sides, exclude, solution);
            boolean result = expected == actual;
            if (result) {
                testsPassed++;
            }
            System.out.println(String.format("testCheckSolution(%s): %s, expected %b", testName, result ? "pass" : "fail", expected));
        } catch (Exception e) {
            System.out.println(String.format("testCheckSolution(%s): fail, crashed %s", testName, e));
        }
    }

    private static void testCheckSolutionMissingY() {
        List<String> solutionMissingY = Arrays.asList(new String[] {"CHINWAG", "GRUMP"});
        testCheckSolution("checkSolutionIncomplete", false, sidesNYT20240323, emptyExclude, solutionMissingY);
    }

    private static void testCheckSolutionExtraO() {
        List<String> solutionExtraO = Arrays.asList(new String[] {"CHINWAG", "GRUMP", "PONY"});
        testCheckSolution("checkSolutionExtraO", false, sidesNYT20240323, emptyExclude, solutionExtraO);
    }

    private static void testCheckSolutionMismatch() {
        List<String> solutionMismatch = Arrays.asList(new String[] {"GRUMPY", "CHINWAG"});
        testCheckSolution("checkSolutionMismatch", false, sidesNYT20240323, emptyExclude, solutionMismatch);
    }

    private static void testCheckSolutionShortWord() {
        List<String> solutionShortWord = Arrays.asList(new String[] {"HA", "ARC", "CAIRN", "NAW", "WAG", "GRUMPY"});
        testCheckSolution("checkSolutionShortWord", false, sidesNYT20240323, emptyExclude, solutionShortWord);
    }

    private static void testCheckSolutionNonword() {
        List<String> solutionNonword = Arrays.asList(new String[] {"CHIN", "NWAG", "GRUMPY"});
        testCheckSolution("checkSolutionNonword", false, sidesNYT20240323, emptyExclude, solutionNonword);
    }

    private static void testCheckSolutionSameSide() {
        List<String> solutionSameSide = Arrays.asList(new String[] {"WING", "GRUMPY", "YAH", "HIC"});
        testCheckSolution("checkSolutionSameSide", false, sidesNYT20240323, emptyExclude, solutionSameSide);
    }

    private static void testCheckSolutionExcluded() {
        HashSet<String> exclude = new HashSet<>(Arrays.asList(new String[] {"GRUMPY"}));
        List<String> solutionExcluded = Arrays.asList(new String[] {"CHINWAG", "GRUMPY"});
        testCheckSolution("checkSolutionIncomplete", false, sidesNYT20240323, exclude, solutionExcluded);
    }

    private static void testCheckSolutionCorrectShort() {
        List<String> solutionCorrectShort = Arrays.asList(new String[] {"CHINWAG", "GRUMPY"});
        testCheckSolution("checkSolutionCorrectShort", true, sidesNYT20240323, emptyExclude, solutionCorrectShort);
    }

    private static void testCheckSolutionCorrectLong() {
        List<String> solutionCorrectLong = Arrays.asList(new String[] {"ACHY", "YAGI", "INARM", "MUMP", "PAW"});
        testCheckSolution("checkSolutionCorrectLong", true, sidesNYT20240323, emptyExclude, solutionCorrectLong);
    }

    private static void testCheckSolutionCorrectLongWithExcludes() {
        HashSet<String> exclude = new HashSet<>(Arrays.asList(new String[] {"CHINWAG", "GRUMPY"}));
        List<String> solutionCorrectLongWithExclude = Arrays.asList(new String[] {"ACHY", "YAGI", "INARM", "MUMP", "PAW"});
        testCheckSolution("checkSolutionCorrectLong", true, sidesNYT20240323, exclude, solutionCorrectLongWithExclude);
    }

    private static void testFindSolutionHelper(String testName, int expectedLength, String[] sides, Set<String> exclude) {
        testsRun++;
        try {
            ArrayList<String> actual = toList(lb.findSolution(sides, exclude));
            if (actual == null) {
                System.out.println(String.format("testFindSolutionHelper(%s): failed, did not find a solution", testName));
                return;
            }
            if (expectedLength != actual.size()) {
                System.out.println(String.format("testFindSolutionHelper(%s): failed, expected length %d, actual length %d", testName, expectedLength, actual.size()));
                return;
            }
            if (lb.checkSolution(sides, exclude, actual)) {
                testsPassed++;
                System.out.println(String.format("testFindSolutionHelper(%s): passed, found %s", testName, actual));
                return;
            } else {
                System.out.println(String.format("testFindSolutionHelper(%s): failed, solution didn't pass checkSolution %s", testName, actual));
                return;
            }
        } catch (Exception e) {
            System.out.println(String.format("testFindSolutionHelper(%s): failed, crashed %s", testName, e));
        }
    }

    private static void testFindSolutionUniqueLengthOne() {
        testsRun++;
        String[] sides = { "AGH", "RIY", "PEC", "OXL" };
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(new String[] {"LEXICOGRAPHY"}));
        try {
            ArrayList<String> actual = toList(lb.findSolution(sides, emptyExclude));
            if (!expected.equals(actual)) {
                System.out.println(String.format("testFindSolutionUniqueLengthOne: failed, expected %s, actual %s", expected, actual));
            } else {
                testsPassed++;
                System.out.println("testFindSolutionUniqueLengthOne: passed, found " + actual);
            }
        } catch (Exception e) {
            System.out.println("testFindSolutionUniqueLengthOne: failed, crashed " + e);
        }
    }
    private static void testFindSolutionUniqueLengthTwo() {
        testsRun++;
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(new String[] {"CHINWAG", "GRUMPY"}));
        ArrayList<String> actual = toList(lb.findSolution(sidesNYT20240323, emptyExclude));
        try {
            if (!expected.equals(actual)) {
                System.out.println(String.format("testFindSolutionUniqueLengthTwo: failed, expected %s, actual %s", expected, actual));
            } else {
                testsPassed++;
                System.out.println("testFindSolutionUniqueLengthTwo: passed, found " + actual);
            }
        } catch (Exception e) {
            System.out.println("testFindSolutionUniqueLengthTwo: failed, crashed " + e);
        }
    }

    private static void testFindSolutionLengthFour() {
        String[] sides = { "QUM", "AXF", "TIK", "PRW" };
        testFindSolutionHelper("findSolutionLengthFour", 4, sides, emptyExclude);
    }

    private static void testFindSolutionLengthSix() {
        String[] sides = { "QUO", "AXZ", "FJK", "PRW" };
        testFindSolutionHelper("findSolutionLengthSix", 6, sides, emptyExclude);
    }

    private static void testFindSolutionLengthSevenWithExclude() {
        String[] sides = { "QUO", "AXZ", "FJK", "PRW" };
        HashSet<String> exclude = new HashSet<>(Arrays.asList(new String[] {"FAUX", "FOX", "FAX"}));
        testFindSolutionHelper("findSolutionLengthSevenWithExclude", 7, sides, exclude);
    }

    private static void testFindSolutionNoSolution() {
        testsRun++;
        String[] sides = { "QUO", "AXZ", "FJK", "PRW" };
        HashSet<String> exclude = new HashSet<>(Arrays.asList(new String[] {"WAQF"}));
        try {
            ArrayList<String> actual = toList(lb.findSolution(sides, exclude));
            if (actual != null) {
                System.out.println(String.format("testFindSolutionNoSolution: failed, expected null, actual %s", actual));
            } else {
                testsPassed++;
                System.out.println("testFindSolutionNoSolution: passed");
            }

        } catch (Exception e) {
            System.out.println("testFindSolutionNoSolution: failed, crashed " + e);
        }
    }

    public static void main(String[] args) {
        try {
            Scanner fileScanner = new Scanner(new FileInputStream("wordlistcs4.txt"));
            lb = new LetterBoxed(fileScanner);
            fileScanner.close();

            // first some tests where lb.checkSolution should return false
            testCheckSolutionMissingY();
            testCheckSolutionExtraO();
            testCheckSolutionMismatch();
            testCheckSolutionShortWord();
            testCheckSolutionNonword();
            testCheckSolutionSameSide();
            testCheckSolutionExcluded();

            // next some tests where lb.checkSolution should return true
            testCheckSolutionCorrectShort();
            testCheckSolutionCorrectLong();
            testCheckSolutionCorrectLongWithExcludes();

            // next a couple tests where lb.findSolution should find a unique solution
            testFindSolutionUniqueLengthOne();
            testFindSolutionUniqueLengthTwo();

            // next some tests where lb.findSolution should find a solution with specific length,
            // and which should also cause lb.checkSolution to return true
            testFindSolutionLengthFour();
            testFindSolutionLengthSix();
            testFindSolutionLengthSevenWithExclude();

            // next a test where lb.findSolution should not find a solution
            testFindSolutionNoSolution();

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
        System.out.println(String.format("%d out of %d tests passed", testsPassed, testsRun));
    } 
}
