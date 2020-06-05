package com.electroninc.basicmusicquiz.question;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.electroninc.basicmusicquiz.question.models.CheckBoxQuestion;
import com.electroninc.basicmusicquiz.question.models.Question;
import com.electroninc.basicmusicquiz.question.models.RadioButtonQuestion;
import com.electroninc.basicmusicquiz.question.models.TextEntryQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QuestionUtils {

    public static final int CHECK_BOX_QUESTION = 1;
    public static final int RADIO_BUTTON_QUESTION = 2;
    public static final int TEXT_ENTRY_QUESTION = 3;
    public static final int UNKNOWN_QUESTION = 4;

    public static int getQuestionType(Question question) {
        if (question instanceof CheckBoxQuestion) return CHECK_BOX_QUESTION;
        else if (question instanceof RadioButtonQuestion) return RADIO_BUTTON_QUESTION;
        else if (question instanceof TextEntryQuestion) return TEXT_ENTRY_QUESTION;
        else return UNKNOWN_QUESTION;
    }

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
            if (type.equals("checkbox")) {
                JSONObject optionsJson = questionJson.getJSONObject("options");
                Map<String, String> options = jsonObjectToStringMap(optionsJson);
                question = new CheckBoxQuestion(questionText, imageAsset, options, answers);
            } else if (type.equals("radio")) {
                JSONObject optionsJson = questionJson.getJSONObject("options");
                Map<String, String> options = jsonObjectToStringMap(optionsJson);
                question = new RadioButtonQuestion(questionText, imageAsset, options, answers);
            } else {
                question = new TextEntryQuestion(questionText, imageAsset, answers);
            }
            questions.add(question);
        }

        Collections.shuffle(questions);
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
        while (keys.hasNext()) {
            String key = keys.next();
            String value = jsonObject.getString(key);
            map.put(key, value);
        }
        return map;
    }

    public static boolean checkAnswer(Set<String> s1, Set<String> s2) { // Pass in any order
        Set<String> sLower1 = new HashSet<>();
        for (String key : s1)
            if (key != null) sLower1.add(key.toLowerCase().trim());
        Set<String> sLower2 = new HashSet<>();
        for (String key : s2)
            if (key != null) sLower2.add(key.toLowerCase().trim());

        Set<String> symmetricDiff = new HashSet<>(sLower1);
        symmetricDiff.addAll(sLower2);
        Set<String> tmp = new HashSet<>(sLower1);
        tmp.retainAll(sLower2);
        symmetricDiff.removeAll(tmp);
        return symmetricDiff.size() == 0;
    }
}
