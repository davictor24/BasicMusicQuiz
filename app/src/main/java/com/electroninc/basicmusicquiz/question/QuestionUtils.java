package com.electroninc.basicmusicquiz.question;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QuestionUtils {
    public static List<Question> compileQuestions(Context context) throws JSONException, IOException {
        // Load JSON from assets folder
        // https://stackoverflow.com/a/19945484
        String json = null;
        InputStream is = context.getAssets().open("questions.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        return loadQuestionsFromJson(context, json);
    }

    public static List<Question> loadQuestionsFromJson(Context context, String json) throws JSONException, IOException {
        List<Question> questions = new ArrayList<>();
        JSONArray questionsJson = new JSONArray(json);

        for (int i = 0; i < questionsJson.length(); i++) {
            Question question;
            JSONObject questionJson = questionsJson.getJSONObject(i);
            String imageAssetFile = questionJson.has("imageAsset") ?
                    questionJson.getString("imageAsset") : null;
            JSONArray answersJson = questionJson.getJSONArray("answers");

            String questionText = questionJson.getString("question");
            Drawable imageAsset = (imageAssetFile != null) ?
                    Drawable.createFromStream(context.getAssets().open(imageAssetFile), null) : null;
            Set<String> answers = jsonArrayToStringSet(answersJson);

            String type = questionJson.getString("type");
            switch (type) {
                case "checkbox":
                case "radio":
                    JSONObject optionsJson = questionJson.getJSONObject("options");
                    Map<String, String> options = jsonObjectToStringMap(optionsJson);
                    question = new CheckBoxQuestion(questionText, imageAsset, options, answers);
                    break;
                case "text":
                    question = new TextEntryQuestion(questionText, imageAsset, answers);
                    break;
                default:
                    continue;
            }
            questions.add(question);
        }

        return questions;
    }

    public static Set<String> jsonArrayToStringSet(JSONArray jsonArray) throws JSONException {
        Set<String> set = new HashSet<>();
        for (int j = 0; j < jsonArray.length(); j++)
            set.add(jsonArray.getString(j));
        return set;
    }

    public static Map<String, String> jsonObjectToStringMap(JSONObject jsonObject) throws JSONException {
        Map<String, String> map = new HashMap<>();
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            String value = jsonObject.getString(key);
            map.put(key, value);
        }
        return map;
    }

    // Utility method for finding set symmetric difference
    // https://stackoverflow.com/a/8064726/8843822
    public static <T> Set<T> diff(final Set<? extends T> s1, final Set<? extends T> s2) {
        Set<T> symmetricDiff = new HashSet<>(s1);
        symmetricDiff.addAll(s2);
        Set<T> tmp = new HashSet<>(s1);
        tmp.retainAll(s2);
        symmetricDiff.removeAll(tmp);
        return symmetricDiff;
    }
}
