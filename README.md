# Big Pickaxes (3x3) - Fabric 26.3

木・石・銅・鉄・ダイヤ・ネザライトの「3×3×1で掘れるつるはし」を追加するMODです。
見た目・耐久・採掘速度・エンチャントは普通のつるはしと同じです。

## ビルド方法
1. JDK 25 をインストールする
2. 公式の fabric-example-mod (26.x) から `gradlew` `gradlew.bat` `gradle/` フォルダをこのフォルダにコピーする
3. このフォルダで `./gradlew build` (Windowsは `gradlew build`) を実行する
4. `build/libs/bigpickaxe-1.0.0.jar` を、Fabric Loader + Fabric API を入れた26.3の `mods` フォルダに入れる

## 遊び方
- つるはしで掘ると、掘った面と平行な3×3のブロックがまとめて壊れます(奥行きは1)
- しゃがんでいても同じように3×3で掘れます
- スポナー・かまど・チェストなど「中身を持つブロック」は巻き込みません
- 周りのブロック1個ごとに、耐久値が普通に減ります

## クラフト
普通のつるはし(同じ素材)を真ん中に置き、上下左右に素材を1個ずつ置きます。
(木は板材、石は丸石系、銅・鉄は金属のインゴット、ダイヤはダイヤ、ネザライトはネザライトインゴット)
レシピは `src/main/resources/data/bigpickaxe/recipe/` で変えられます。

## パソコンに何も入れずにjarを作る方法(GitHub Actions)
1. GitHubのアカウントを作り、新しいリポジトリを作る
2. このフォルダの中身(`.github` フォルダも含めて)をすべてアップロードする
3. リポジトリの「Actions」タブで `build` が終わるのを待つ(数分)
4. 完了した実行を開き、下の「Artifacts」から `bigpickaxe-jar` をダウンロードする
5. 中の `bigpickaxe-1.0.0.jar` を `mods` フォルダに入れる(`-sources` が付いたjarは不要)
