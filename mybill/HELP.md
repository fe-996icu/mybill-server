# mybill

个人记账系统

> SpringBoot with MyBatisPlus
>
> Java JDK Version 17
>

## Controller 接收枚举类型参数，需要做数据转换说明
`com.icu.mybill.util.EnumUtils.ValuedEnum`
1. 创建枚举类：`com.icu.mybill.enums.BillQueryDateRange.java`，需要实现`com.icu.mybill.util.EnumUtils.ValuedEnum`接口。
    ```java
    @Getter
    @AllArgsConstructor
    public enum BillQueryDateRange implements EnumUtils.ValuedEnum {
        DEFAULT(1, "所有"),
        WEEK(2, "周"),
        MONTH(3, "月"),
        YEAR(4, "年");
    
        @JsonValue // 告诉jackson，序列化时，返回给前端的value，因为springboot用的是jackson
        @EnumValue // 告诉MybatisPlus，数据库存的是value
        private final Integer value;
        private final String desc;
    }
    ```
2. 创建枚举类型转换器类：`com.icu.mybill.converter.StringToBillQueryDateRangeConverter.java`
    ```java
    @Component
    public class StringToBillQueryDateRangeConverter implements Converter<String, BillQueryDateRange> {
        @Override
        public BillQueryDateRange convert(@NotNull String source) {
            return EnumUtils.fromValue(source, BillQueryDateRange.class);
        }
    }
    ```
3. 在`com.icu.mybill.config.WebConfig.java`类中注入依赖。
    ```java
    // 注册字符串转枚举类型转换器
    @Autowired
    private StringToBillQueryDateRangeConverter stringToBillQueryDateRangeConverter;
    ```


## DateTimeFormatter.ofPattern 支持的格式模式

`DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")` 是 Java 中用于格式化 `LocalDateTime` 或 `ZonedDateTime` 的一种模式。`ofPattern` 方法支持 丰富的格式化模式：

**1. 常见的日期时间模式**
-----------------

| **模式** | **示例** | **含义** |
| --- | --- | --- |
| `yyyy-MM-dd` | `2025-03-05` | 年-月-日 |
| `yyyy/MM/dd` | `2025/03/05` | 斜杠分隔的日期 |
| `yyyyMMdd` | `20250305` | 无分隔符日期 |
| `yy-MM-dd` | `25-03-05` | 两位数年份 |
| `MM-dd-yyyy` | `03-05-2025` | 月-日-年 |
| `dd/MM/yyyy` | `05/03/2025` | 日/月/年 |

* * *

**2. 带时间的模式**
--------------

| **模式** | **示例** | **含义** |
| --- | --- | --- |
| `HH:mm:ss` | `14:30:45` | 24小时制时:分:秒 |
| `hh:mm:ss a` | `02:30:45 PM` | 12小时制时:分:秒 AM/PM |
| `HH:mm:ss.SSS` | `14:30:45.123` | 毫秒（3位数） |
| `HH:mm:ss.SSSSSS` | `14:30:45.123456` | 微秒（6位数） |
| `HH:mm:ss.SSSSSSSSS` | `14:30:45.123456789` | 纳秒（9位数） |
| `hh:mm a` | `02:30 PM` | 12小时制时:分 AM/PM |

* * *

**3. 带时区的模式**
--------------

| **模式** | **示例** | **含义** |
| --- | --- | --- |
| `yyyy-MM-dd HH:mm:ss Z` | `2025-03-05 14:30:45 +0800` | 带时区偏移量 |
| `yyyy-MM-dd HH:mm:ss z` | `2025-03-05 14:30:45 CST` | 时区缩写 |
| `yyyy-MM-dd HH:mm:ss XXX` | `2025-03-05 14:30:45 +08:00` | ISO 8601 时区格式 |
| `yyyy-MM-dd HH:mm:ss VV` | `2025-03-05 14:30:45 Asia/Shanghai` | 时区名称 |

* * *

**4. 自定义格式组合**
---------------

| **模式** | **示例** | **说明** |
| --- | --- | --- |
| `EEEE, MMMM dd, yyyy` | `Wednesday, March 05, 2025` | 完整星期几、月份、日期、年份 |
| `EEE, MMM dd yyyy` | `Wed, Mar 05 2025` | 简写星期几、月份、日期、年份 |
| `'今天是：'yyyy年MM月dd日` | `今天是：2025年03月05日` | 自定义前缀 |
| `yyyy/MM/dd 'at' HH:mm` | `2025/03/05 at 14:30` | 自定义文本间隔 |

* * *

**5. 特殊符号说明**
--------------

| **符号** | **含义** | **示例** |
| --- | --- | --- |
| `y` | 年（4位或2位） | `yyyy: 2025`, `yy: 25` |
| `M` | 月（1-12 或 Jan-Dec） | `M: 3`, `MM: 03`, `MMM: Mar`, `MMMM: March` |
| `d` | 日（1-31） | `d: 5`, `dd: 05` |
| `E` | 星期（短/长格式） | `E: Wed`, `EEEE: Wednesday` |
| `H` | 24小时制（0-23） | `H: 5`, `HH: 05` |
| `h` | 12小时制（1-12） | `h: 5`, `hh: 05` |
| `m` | 分钟（0-59） | `m: 9`, `mm: 09` |
| `s` | 秒（0-59） | `s: 2`, `ss: 02` |
| `S` | 毫秒（1-9 位数） | `SSS: 123` |
| `a` | 上午/下午 | `a: AM/PM` |
| `z` | 时区缩写 | `CST` |
| `X` | ISO 8601 时区 | `+08:00` |
| `V` | 具体时区 | `Asia/Shanghai` |
| `'文本'` | 直接输出文本 | `'Year:' yyyy → Year: 2025` |
