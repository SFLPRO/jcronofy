package com.sfl.cronofy.api.client.impl;

import com.sfl.cronofy.api.client.AbstractCronofyClient;
import com.sfl.cronofy.api.client.CronofyClient;
import com.sfl.cronofy.api.model.common.AbstractAccessTokenAwareCronofyRequest;
import com.sfl.cronofy.api.model.common.AbstractCronofyRequest;
import com.sfl.cronofy.api.model.common.CronofyResponse;
import com.sfl.cronofy.api.model.common.ErrorTypeModel;
import com.sfl.cronofy.api.model.request.*;
import com.sfl.cronofy.api.model.response.*;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 * User: Arthur Asatryan
 * Company: SFL LLC
 * Date: 10/5/16
 * Time: 11:44 AM
 */
public class CronofyClientImpl extends AbstractCronofyClient implements CronofyClient {

    //region Constants
    private static final String BASE_PATH = "https://api.cronofy.com";

    private static final String API_VERSION = "v1";

    private static final String CALENDARS_PATH = "calendars";

    private static final String CHANNELS_PATH = "channels";

    private static final String PROFILES_PATH = "profiles";

    private static final String AUTH_HEADER_KEY = "Authorization";

    private static final String ACCOUNT_PATH = "account";

    private static final String EVENTS = "events";
    //endregion

    //region Constructors
    public CronofyClientImpl(final Client client) {
        super(client);
    }
    //endregion

    //region Public methods
    public CronofyResponse<GetAccessTokenResponse> getAccessToken(final GetAccessTokenRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path("oauth")
                    .path("token")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE),
                            new GenericType<CronofyResponse<GetAccessTokenResponse>>() {
                            }
                    );
        } catch (final BadRequestException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.BAD_REQUEST);
        }
    }

    public CronofyResponse<UpdateAccessTokenResponse> updateAccessToken(final UpdateAccessTokenRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path("oauth")
                    .path("token")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE),
                            new GenericType<CronofyResponse<UpdateAccessTokenResponse>>() {
                            }
                    );
        } catch (final BadRequestException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.BAD_REQUEST);
        }
    }

    public CronofyResponse<RevokeAccessTokenResponse> revokeAccessToken(final RevokeAccessTokenRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path("oauth")
                    .path("token")
                    .path("revoke")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE),
                            new GenericType<CronofyResponse<RevokeAccessTokenResponse>>() {
                            }
                    );
        } catch (final BadRequestException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.BAD_REQUEST);
        }
    }

    public CronofyResponse<ListCalendarsResponse> listCalendars(final ListCalendarsRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path(API_VERSION)
                    .path(CALENDARS_PATH)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(AUTH_HEADER_KEY, getAccessTokenFromRequest(request))
                    .get(new GenericType<CronofyResponse<ListCalendarsResponse>>() {
                    });
        } catch (final NotAuthorizedException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.NOT_AUTHORIZED);
        } catch (final BadRequestException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.BAD_REQUEST);
        } catch (final ClientErrorException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.UNPROCESSABLE);
        }
    }

    public CronofyResponse<ReadEventsResponse> readEvents(final ReadEventsRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path(API_VERSION)
                    .path(EVENTS)
                    .queryParam("from", request.getFrom())
                    .queryParam("to", request.getTo())
                    .queryParam("tzid", request.getTzId())
                    .queryParam("include_deleted", request.isIncludeDeleted())
                    .queryParam("include_moved", request.isIncludeMoved())
                    .queryParam("last_modified", request.getLastModified())
                    .queryParam("include_managed", request.isIncludeManaged())
                    .queryParam("only_managed", request.isOnlyManaged())
                    .queryParam("calendar_ids", request.getCalendarIds())
                    .queryParam("localized_times", request.isLocalizedTimes())
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(AUTH_HEADER_KEY, getAccessTokenFromRequest(request))
                    .get(new GenericType<CronofyResponse<ReadEventsResponse>>() {
                    });
        } catch (final NotAuthorizedException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.NOT_AUTHORIZED);
        } catch (final ForbiddenException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.FORBIDDEN);
        } catch (final BadRequestException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.BAD_REQUEST);
        } catch (final ClientErrorException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.UNPROCESSABLE);
        }
    }

    public CronofyResponse<FreeBusyResponse> freeBusy(final FreeBusyRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path(API_VERSION)
                    .path("free_busy")
                    .queryParam("from", request.getFrom())
                    .queryParam("to", request.getTo())
                    .queryParam("tzid", request.getTzId())
                    .queryParam("include_managed", request.getIncludeManaged())
                    .queryParam("calendar_ids", request.getCalendarIds())
                    .queryParam("localized_times", request.getLocalizedTimes())
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(AUTH_HEADER_KEY, getAccessTokenFromRequest(request))
                    .get(new GenericType<CronofyResponse<FreeBusyResponse>>() {
                    });
        } catch (final NotAuthorizedException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.NOT_AUTHORIZED);
        } catch (final ForbiddenException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.FORBIDDEN);
        } catch (final BadRequestException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.BAD_REQUEST);
        } catch (final ClientErrorException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.UNPROCESSABLE);
        }
    }

    public CronofyResponse<CreateOrUpdateEventResponse> createOrUpdateEvent(final CreateOrUpdateEventRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path(API_VERSION)
                    .path(CALENDARS_PATH)
                    .path(request.getCalendarId())
                    .path(EVENTS)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(AUTH_HEADER_KEY, getAccessTokenFromRequest(request))
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE),
                            new GenericType<CronofyResponse<CreateOrUpdateEventResponse>>() {
                            }
                    );
        } catch (final NotAuthorizedException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.NOT_AUTHORIZED);
        } catch (final ForbiddenException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.FORBIDDEN);
        } catch (final BadRequestException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.BAD_REQUEST);
        } catch (final ClientErrorException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.UNPROCESSABLE);
        } catch (final ResponseProcessingException ignore) {
            return new CronofyResponse<>(new CreateOrUpdateEventResponse());
        }
    }

    public CronofyResponse<DeleteEventResponse> deleteEvent(final DeleteEventRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path(API_VERSION)
                    .path(CALENDARS_PATH)
                    .path(request.getCalendarId())
                    .path(EVENTS)
                    .queryParam("event_id", request.getEventId())
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(AUTH_HEADER_KEY, getAccessTokenFromRequest(request))
                    .delete(new GenericType<CronofyResponse<DeleteEventResponse>>() {
                    });
        } catch (final NotAuthorizedException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.NOT_AUTHORIZED);
        } catch (final ForbiddenException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.FORBIDDEN);
        } catch (final BadRequestException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.BAD_REQUEST);
        } catch (final ClientErrorException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.UNPROCESSABLE);
        } catch (final ResponseProcessingException ignore) {
            return new CronofyResponse<>(new DeleteEventResponse());
        }
    }

    public CronofyResponse<CreateNotificationChannelResponse> createNotificationChannel(final CreateNotificationChannelRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path(API_VERSION)
                    .path(CHANNELS_PATH)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(AUTH_HEADER_KEY, getAccessTokenFromRequest(request))
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE),
                            new GenericType<CronofyResponse<CreateNotificationChannelResponse>>() {
                            }
                    );
        } catch (final NotAuthorizedException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.NOT_AUTHORIZED);
        } catch (final ClientErrorException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.UNPROCESSABLE);
        }
    }

    public CronofyResponse<ListNotificationChannelsResponse> listNotificationChannels(final ListNotificationChannelsRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path(API_VERSION)
                    .path(CHANNELS_PATH)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(AUTH_HEADER_KEY, getAccessTokenFromRequest(request))
                    .get(new GenericType<CronofyResponse<ListNotificationChannelsResponse>>() {
                    });
        } catch (final NotAuthorizedException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.NOT_AUTHORIZED);
        }
    }

    public CronofyResponse<CloseNotificationChannelResponse> closeNotificationChannel(final CloseNotificationChannelRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path(API_VERSION)
                    .path(CHANNELS_PATH)
                    .path(request.getChannelId())
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(AUTH_HEADER_KEY, getAccessTokenFromRequest(request))
                    .delete(new GenericType<CronofyResponse<CloseNotificationChannelResponse>>() {
                    });
        } catch (final NotAuthorizedException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.NOT_AUTHORIZED);
        }
    }

    public CronofyResponse<AccountInfoResponse> accountInfo(final AccountInfoRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path(API_VERSION)
                    .path(ACCOUNT_PATH)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(AUTH_HEADER_KEY, getAccessTokenFromRequest(request))
                    .get(new GenericType<CronofyResponse<AccountInfoResponse>>() {
                    });
        } catch (final NotAuthorizedException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.NOT_AUTHORIZED);
        }
    }

    public CronofyResponse<ProfileInformationResponse> profileInfo(final ProfileInformationRequest request) {
        assertCronofyRequest(request);
        try {
            return getClient()
                    .target(BASE_PATH)
                    .path(API_VERSION)
                    .path(PROFILES_PATH)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(AUTH_HEADER_KEY, getAccessTokenFromRequest(request))
                    .get(new GenericType<CronofyResponse<ProfileInformationResponse>>() {
                    });
        } catch (final NotAuthorizedException ignore) {
            return new CronofyResponse<>(ErrorTypeModel.NOT_AUTHORIZED);
        }
    }
    //endregion

    //region Utility methods
    private <T extends AbstractAccessTokenAwareCronofyRequest> String getAccessTokenFromRequest(final T request) {
        return "Bearer " + request.getAccessToken();
    }

    private void assertCronofyRequest(final AbstractCronofyRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("The cronofy request should not be null");
        }
        if (request instanceof AbstractAccessTokenAwareCronofyRequest
                && StringUtils.isBlank(((AbstractAccessTokenAwareCronofyRequest) request).getAccessToken())) {
            throw new IllegalArgumentException("The access token should not be blank");
        }
    }
    //endregion
}