/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.serviceagent.workbench;

import static cn.zhuatech.serviceagent.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "workStart", "workEnd", "holidays", "tickets");
    int start = integer(r, "workStart", 0, 22), end = integer(r, "workEnd", 1, 23);
    if (start >= end) throw new IllegalArgumentException("办公结束时间须晚于开始时间");
    Set<java.time.LocalDate> holidays = new HashSet<>();
    for (String s : strings(r, "holidays", 0, 60))
      if (!holidays.add(date(s))) throw new IllegalArgumentException("节假日重复");
    List<Map<String, Object>> out = new ArrayList<>();
    Set<String> ids = new HashSet<>();
    for (var t : rows(r, "tickets", 1, 100)) {
      keys(t, "id", "receivedAt", "budgetMinutes");
      String id = id(t, "id");
      unique(ids, id);
      var at = local(text(t, "receivedAt", 30));
      int remaining = integer(t, "budgetMinutes", 1, 10000);
      var cursor = at;
      int days = 0;
      while (remaining > 0) {
        if (++days > 800) throw new IllegalArgumentException("日历跨度过长");
        var d = cursor.toLocalDate();
        if (d.getDayOfWeek().getValue() > 5 || holidays.contains(d)) {
          cursor = d.plusDays(1).atTime(start, 0);
          continue;
        }
        if (cursor.isBefore(d.atTime(start, 0))) cursor = d.atTime(start, 0);
        if (!cursor.isBefore(d.atTime(end, 0))) {
          cursor = d.plusDays(1).atTime(start, 0);
          continue;
        }
        long usable = java.time.Duration.between(cursor, d.atTime(end, 0)).toMinutes();
        if (usable < 1) {
          cursor = d.plusDays(1).atTime(start, 0);
          continue;
        }
        int take = (int) Math.min(remaining, usable);
        cursor = cursor.plusMinutes(take);
        remaining -= take;
      }
      out.add(
          Map.of(
              "id",
              id,
              "receivedAt",
              at.toString(),
              "dueAt",
              cursor.toString(),
              "budgetMinutes",
              t.get("budgetMinutes")));
    }
    return Map.of("timeBasis", "ORGANIZATION_LOCAL", "tickets", out);
  }
}
