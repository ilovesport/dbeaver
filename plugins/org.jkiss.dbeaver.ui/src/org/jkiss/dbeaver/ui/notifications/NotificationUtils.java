/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2019 Serge Rider (serge@jkiss.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ui.notifications;

import org.eclipse.mylyn.commons.notifications.core.AbstractNotification;
import org.eclipse.mylyn.commons.notifications.ui.NotificationsUi;
import org.jkiss.dbeaver.Log;
import org.jkiss.dbeaver.ModelPreferences;
import org.jkiss.dbeaver.model.DBPDataSource;
import org.jkiss.dbeaver.model.DBPMessageType;

import java.util.Collections;

public abstract class NotificationUtils {

    private static final Log log = Log.getLog(NotificationUtils.class);

    public static void sendNotification(DBPDataSource dataSource, String id, String text) {
        sendNotification(dataSource, id, text, null, null);
    }

    public static void sendNotification(DBPDataSource dataSource, String id, String text, DBPMessageType messageType, Runnable feedback) {
        if (!ModelPreferences.getPreferences().getBoolean(ModelPreferences.NOTIFICATIONS_ENABLED)) {
            return;
        }
        AbstractNotification notification = new DatabaseNotification(
            dataSource,
            id,
            text,
            messageType,
            feedback);
        try {
            NotificationsUi.getService().notify(
                Collections.singletonList(notification));
        } catch (Exception e) {
            log.debug("Error sending Mylin notification", e);
        }
    }

    public static void sendNotification(String id, String title, String text, DBPMessageType messageType, Runnable feedback) {
        if (!ModelPreferences.getPreferences().getBoolean(ModelPreferences.NOTIFICATIONS_ENABLED)) {
            return;
        }
        AbstractNotification notification = new GeneralNotification(
            id,
            title,
            text,
            messageType,
            feedback);
        try {
            NotificationsUi.getService().notify(
                Collections.singletonList(notification));
        } catch (Exception e) {
            log.debug("Error sending Mylin notification", e);
        }
    }

}