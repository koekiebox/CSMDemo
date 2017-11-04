package com.backbase.faces;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;
import java.util.Map;

/**
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 */
public class ViewExpiredExceptionExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    /**
     *
     * @param handlerParam
     */
    public ViewExpiredExceptionExceptionHandler(ExceptionHandler handlerParam) {
        this.wrapped = handlerParam;
    }

    /**
     *
     * @return
     */
    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    /**
     *
     * @throws FacesException
     */
    @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> eventIterator = getUnhandledExceptionQueuedEvents().iterator(); eventIterator.hasNext();) {
            ExceptionQueuedEvent event = eventIterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable throwable = context.getException();

            //For View Expired...
            if ((throwable instanceof ViewExpiredException)) {

                //ViewExpiredException vee = (ViewExpiredException) throwable;
                FacesContext fc = FacesContext.getCurrentInstance();
                Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
                NavigationHandler nav =
                        fc.getApplication().getNavigationHandler();
                try {
                    // Push some useful stuff to the request scope for
                    // use in the page
                    //requestMap.put("currentViewId", vee.getViewId());
                    String fromAction = null;

                    nav.handleNavigation(fc, fromAction, "expired");
                    fc.renderResponse();
                }
                //
                finally {
                    eventIterator.remove();
                }
            }

            /*
            String errorPageLocation = "/WEB-INF/errorpages/expired.xhtml";
            context.setViewRoot(context.getApplication().getViewHandler().createView(context, errorPageLocation));
            context.getPartialViewContext().setRenderAll(true);
            context.renderResponse();
            */
        }
        // At this point, the queue will not contain any ViewExpiredEvents.
        // Therefore, let the parent handle them.
        getWrapped().handle();

    }
}
