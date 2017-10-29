package com.kjh.seoulapp.data;

import java.util.ArrayList;
import java.util.List;

public class SharedData
{
	public enum POPUP_TYPE { APP_INFO, CONTACT, DONATE, END_QUIZ }

	public static final String USER_REF = "user";
	public static final String CULTURAL_REF = "cultural";

	public static final String EXTRA_POPUP_TYPE = "POPUP_TYPE";
	public static final String EXTRA_CORRECT_CNT = "correctCnt";

	public static UserData userData;
	public static List<ProblemData> probList = new ArrayList<>();
	public static int correctCnt;
	public static int regionIndex;
	public static int stampLevel;
}
