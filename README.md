Jersey-app: simple jetty-jersey application written mostly by AntiGravity CLI and Visual Studio Code Copilot.
Released under GPLv3.

java jersey rest アプリケーションのお勉強。AI コーディングアシスト、すごい。
しかし、半日で無料枠を使い切りそう。

# やったこと

リソースハンドラ
world テキスト world を返す
hello query string を使う。pam 認証を必要とする。
getenv サーブレットが使うことのできるコンテキストを表示する。
concat json で得た文字列の配列を連結して json で返す。
books sqlite で、テーブルのエントリ一覧表示とエントリ追加

## ssl
java -jar /opt/jetty-home-12.1.12/start.jar --add-module=ssl
java -jar /opt/jetty-home-12.1.12/start.jar --add-module=https
keytool で、自己署名の鍵を作る。CN=localhost
ssl-context.ini に、鍵ファイルのパス、パスワードを書く。

