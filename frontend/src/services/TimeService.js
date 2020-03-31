export default function timeElapsedSincePosted(date) {
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
    else if (timePosted < 604800) {
        //Display in days
        timePosted = (timePosted / (60 * 60 * 24)).toFixed(0);
        timeFormat = "days";
    }
    else if (timePosted < 31449600) {
        //Display in weeks
        timePosted = (timePosted / (60 * 60 * 24 * 7)).toFixed(0);
        timeFormat = "w";
    }
    else {
        //Display in years
        timePosted = (timePosted / (60 * 60 * 24 * 7 * 52)).toFixed(0);
        timeFormat = "y";
    }

    return timePosted + " " + timeFormat;
}