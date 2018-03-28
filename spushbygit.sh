#!/bin/sh

function log() {
    echo -e '\n'$1'\n'
}

function presstoexit() {
    echo -e '\nplease press any key to exit.'
    read
}

DATE_TIME=`date +%Y%m%d\ %H:%m:%s`

git status
log 'git status over !'

git add . && git commit -m "push on $DATE_TIME"
log 'git commit successful !'

git push -u origin master:master
log "git push successful !"

presstoexit
