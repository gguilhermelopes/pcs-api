package com.psyclinicSolutions.services.helpers;

import com.psyclinicSolutions.domain.Session;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class SessionValidator {
    public boolean isSessionOverlapping(Instant newSessionStart, Instant newSessionEnd, List<Session> therapistSessions, Session sessionToIgnore) {
        for (Session existingSession : therapistSessions) {
            if (sessionToIgnore != null && existingSession.getId().equals(sessionToIgnore.getId())) {
                continue;
            }

            Instant existingSessionStart = existingSession.getSessionDate();
            Instant existingSessionEnd = existingSessionStart.plusSeconds(existingSession.getSessionDuration() * 60);

            if (newSessionStart.isBefore(existingSessionEnd) && newSessionEnd.isAfter(existingSessionStart)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSessionOffWorkingHours(Instant newSessionStart, Instant newSessionEnd, LocalTime startWorkingHours, LocalTime endWorkingHours, ZoneId zoneId) {
        LocalTime sessionStartTime = newSessionStart.atZone(zoneId).toLocalTime();
        LocalTime sessionEndTime = newSessionEnd.atZone(zoneId).toLocalTime();

        return sessionStartTime.isBefore(startWorkingHours) || sessionEndTime.isAfter(endWorkingHours);
    }

    public boolean isPaidCheckedAndPaymentDateNotNull(Session session) {
        return session.getIsPaid() && session.getPaymentDate() != null;
    }

    public boolean isAccountedCheckedAndAccountDateNotNull(Session session) {
        return session.getIsAccounted() && session.getAccountDate() != null;
    }

    public boolean isAuthorizedCheckedAndAuthorizationDateOrTokenNotNull(Session session) {
        return session.getIsAuthorized() && session.getAuthorizationDate() != null && session.getToken() != null;
    }

}
