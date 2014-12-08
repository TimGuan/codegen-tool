if [ -z "$JAVA_HOME" ]; then
  JAVA_HOME=/opt/haitao/install/jdk1.7.0_71
fi
JAVA="$JAVA_HOME/bin/java"
function main {
   RETVAL=0
   case "$1" in
      JAVA)
         echo $JAVA
         $JAVA -Djava.ext.dirs=lib com.sfebiz.codegenTool.Main JAVA
         ;;
      OBJC)
         $JAVA -Djava.ext.dirs=lib com.sfebiz.codegenTool.Main OBJC
         ;;
      JS)
         $JAVA -Djava.ext.dirs=lib com.sfebiz.codegenTool.Main JS
         ;;
      *)
         echo "Usage: $0 {JAVA|OBJC|JS}"
         exit 1
         ;;
      esac
   exit $RETVAL
}
main $1
