package com.electroninc.basicmusicquiz.question;

import java.util.HashSet;
import java.util.Set;

class QuestionUtils {
    // Utility method for finding set symmetric difference
    // https://stackoverflow.com/a/8064726/8843822
    static <T> Set<T> diff(final Set<? extends T> s1, final Set<? extends T> s2) {
        Set<T> symmetricDiff = new HashSet<>(s1);
        symmetricDiff.addAll(s2);
        Set<T> tmp = new HashSet<>(s1);
        tmp.retainAll(s2);
        symmetricDiff.removeAll(tmp);
        return symmetricDiff;
    }
}
