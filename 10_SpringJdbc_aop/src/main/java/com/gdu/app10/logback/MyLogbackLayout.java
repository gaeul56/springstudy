package com.gdu.app10.logback;

import java.text.SimpleDateFormat;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

public class MyLogbackLayout extends LayoutBase<ILoggingEvent> {

  @Override
  public String doLayout(ILoggingEvent event) {
    // 레이아웃 메서드로 Logback 이벤트를 받아서 원하는 형식으로 레이아웃합니다.

    StringBuilder sb = new StringBuilder();

    sb.append("["); // 로그 메시지 시작 부분
    sb.append(new SimpleDateFormat("HH:mm:ss").format(event.getTimeStamp())); // 현재 시간을 HH:mm:ss 형식으로 추가
    sb.append("] "); // 시간 형식 이후 공백 추가
    sb.append(String.format("%-5s", event.getLevel())); // 로그 레벨을 포맷팅하여 추가
    sb.append(":"); // 로그 레벨 이후에 콜론 추가

    String loggerName = event.getLoggerName(); // 로거 이름 가져오기
    sb.append(loggerName);

    if (loggerName.equals("jdbc.sqlonly")) {
      sb.append("\n"); // 로거 이름이 "jdbc.sqlonly" 인 경우 개행 추가
    } else {
      sb.append(" - "); // 그 외의 경우 " - " 추가
    }

    sb.append(event.getFormattedMessage()); // 로그 메시지 추가
    sb.append("\n"); // 개행 추가

    return sb.toString(); // 완성된 로그 메시지 문자열 반환
  }
}