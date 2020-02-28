import React, {useState, useEffect} from 'react'
import { Link } from "react-router-dom"
import './SCSS/Comment.scss'
export const Comment = (props) => {
    const [timePosted, setTimePosted] = useState(0);
    const [timeFormat, setTimeFormat] = useState("sec");
    
    useEffect(() => {
        timeElapseSincePosted();
    }, [])
    const timeElapseSincePosted = () => {
        let currentDate = new Date();
        let timePosted = currentDate.getTime() - new Date(props.comment.created).getTime();
        timePosted = (timePosted / 1000).toFixed(0);

        if(timePosted < 3600){
            //Display in minutes
            timePosted = (timePosted/60).toFixed(0);
            setTimeFormat("min");
        }
        else if(timePosted <86400){
            //Display in hours
            timePosted = (timePosted/(60*60)).toFixed(0);
            setTimeFormat("h");
        }
        else if(timePosted >= 86400){
            //Display in days
            timePosted = (timePosted/(60*60*24)).toFixed(0);
            setTimeFormat("days");
        }
                
        setTimePosted(timePosted);
    }
    return (
        <div className="comment-div">
            <br />
            <Link to={`/account/${props.comment.account}`}>{props.comment.account} </Link> {props.comment.comment}
            <div className="date-created">{timePosted} {timeFormat} ago</div>
            <br />
        </div>
    )
}