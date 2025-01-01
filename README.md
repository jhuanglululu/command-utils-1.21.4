
# Command Utils

**一個 1.21.4 的 Fabric 模組**
* .mcfunction 搜尋
* .mcfunction 註釋（docs/documentation)

**優點**
* 不用在記得所有的function在哪和如何使用
* 只需要安裝在伺服器 超適合多人協作
* 裝了你就是專家

## 備註
因為有使用到其他人的代碼 所以`github` 上不會放 .jar 載點 如果需要模組可以自己下載原始碼來安裝或是透過 `Discord` 聯繫 `jhuanglululu`

## 指令展示 (listfunction)
<h1>
  <img src="listfunction.png">
</h1>


## 指令展示 (docs)
<h1>
  <img src="docsexample.png">
</h1>

# 使用教學 (docs)
NOTE: 請至少會寫 `datapack`

## 1. 開頭 DOCS

為了讓模組區分一般的標記和註釋需要在整個 `mcfunction` 的第一行以 `# @DOCS` 開頭

NOTE: `#` 和 `@DOCS` 中間有一個空格
```mclang
# @Docs
# Other docs
# ...

your datapack code
...
```

非 `#` 開頭的第一行代表註釋結束

NOTE: `#` 後面需要跟一個空格

## 2. 標籤 Tags< >

在 `# @Docs` 與 `# @Tags` 後可以加入標籤來給屬性加入更多註釋

`# @Docs` 的標籤：

| @Docs 的屬性   | < 輸入值 >     | NOTE                          |
|-------------|-------------|-------------------------------|
| ABSTRACT    | | 摘要函式（？） 沒有寫東西的
| ADVANCEMENT | advancement | 用於成就獎勵 輸入 advancement 點擊時獲得成就 |
| CLICKEVENT  |             | 用於 clickevent                 |
| DEPRECATED  | alternative | 棄置 輸入 alternative 點擊時獲得註釋     |
| DEMO        |             | 示範用                           |
| ELSE        | parent      | Else 分支 輸入 parent 來找到母函式      |
| EXCEPTION   | exception   | 處理意外 輸入 exception 來顯示意外情境     |
| IF          | parent      | If 分支 輸入 parent 來找到母函式        | 
| IMPLEMENT   | abstract | 實現（？） 寫完 Abstract 的函式 輸入 abstract 點擊時或的註釋
| INIT        | | 初始化數值的函式
| LOAD        |             | /reload 後執行                   |
| MACRO       |             | macro 函式                      |
| PRIVATE     | |不應該被外部使用
| RECURSIVE   |             | 遞迴函式（俄羅斯娃娃）                   |
| TEAM        | team        | 隊伍專屬 輸入來顯示隊伍名稱                |
| TEMPLATE    |             | 模板                            |
| TEST        |             | 測試用函式                         |
| TICK        |             | 每個 tick 都會執行                  |

NOTE: 以後還會增加

使用方法
```mclang
# @Docs DEPRECATED<jhuanglululu:new_function> RECURSIVE IF
# 標籤會跟在屬性後面 距離一個空格
# 輸入值會緊貼著標籤並且用 < > 包圍
# 標籤與標籤之間距離一個空個
# 輸入值不是必須
# Other docs
# ...

your datapack code
...
```
自定義標籤
```mclang
# @Docs !MYCUSTOMTAG<Some description about the tag>
# 自定義標籤用 ! 來開頭 後面輸入懸停顯示文字
# Other docs
# ...

your datapack code
...
```

## 3. 屬性 @ Properties

- Author
- Date
- Comment
- Purpose
- Example
- Variable
- Require
- Ensure
- Update
- Return
- Reference
- See Also
- Kpop

# TO BE CONTINUE
