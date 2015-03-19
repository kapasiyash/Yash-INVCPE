TODAY=`(set \`date +%Y_%m_%d_%H%M%S\`; echo $1)`
LOG=Installation_$TODAY.log
ERROR=Error_$TODAY.log

mkdir installationdir

while getopts rvdx OPTION
do
    case ${OPTION} in
        r) reinstall_db_flag=true;;
        v) verbose_flag=true;;
        d) debug_flag=true;;
        x) skip_version_check=true;;
    esac
done

shift $(($OPTIND - 1))

command="-f install.xml "

if [ "$verbose_flag" = "true" ] || [ "$debug_flag" = "true" ] ; then

        if [ "$debug_flag" = "true" ] ; then
                command=`echo $command -debug`
        fi

else
        command=`echo $command -quiet -emacs`
fi

if [ "$reinstall_db_flag" = "true" ] ; then
        command=`echo $command -Dreinstall.db.version=true`
fi

if [ "$skip_version_check" = "true" ] ; then
        command=`echo $command -Dskip.version.check=true`
fi

pwd=`pwd`
command=`echo $command -Dfile.path=$pwd -Dfile.list=$1 -Dinstallation.dir=$pwd/installationdir`

${ANT_HOME}/bin/ant $command 1>$LOG 2>$ERROR &
