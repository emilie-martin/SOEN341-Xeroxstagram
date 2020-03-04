export const timeElapseSincePosted = (date) => {
    let currentDate = new Date();
    let timeFormat = "sec";
    let timePosted = currentDate.getTime() - date.getTime();
    timePosted = (timePosted / 1000).toFixed(0);

    if (timePosted < 60) {
        return timePosted + " " + timeFormat;
    }
    else if (timePosted < 3600) {
        //Display in minutes
        timePosted = (timePosted / 60).toFixed(0);
        timeFormat = "min";
    }
    else if (timePosted < 86400) {
        //Display in hours
        timePosted = (timePosted / (60 * 60)).toFixed(0);
        timeFormat = "h";
    }
    else if (timePosted < 1036800) {
        //Display in days
        timePosted = (timePosted / (60 * 60 * 24)).toFixed(0);
        timeFormat = "days";
    }
    else if (timePosted >= 1036800) {
        timePosted = (timePosted / (60 * 60 * 24 * 12)).toFixed(0);
        timeFormat = "years";
    }

    return timePosted + " " + timeFormat;
}