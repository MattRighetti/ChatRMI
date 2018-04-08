#!/bin/bash
basedir=`pwd`/out/production/ChatRMI

    say initiating
  # client
    cp $basedir/Client.class ./classloading/client/
    cp $basedir/TextView.class ./classloading/client/

  # server
    cp $basedir/Server.class ./classloading/server/
    cp $basedir/Controller.class ./classloading/server/


  # codebase
    cp $basedir/RemoteController.class ./classloading/common/
    cp $basedir/Database.class ./classloading/common/
    cp $basedir/Message.class ./classloading/common/
    cp $basedir/Group.class ./classloading/common/
    cp $basedir/User.class ./classloading/common/
    cp $basedir/RemoteTextView.class ./classloading/common/
    cp $basedir/MessageObserver.class ./classloading/common/