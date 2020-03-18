import '../../config';
import React, { useState, useEffect } from 'react';
import axios from "axios";

// Stylesheet
import './FollowingButton.scss'

export default function FollowingButton(props)
{
    const [isFollowing, setFollowing] = useState(false);
    const [errorMsg, setErrorMsg] = useState("");

    const follow = (event) => {
        event.preventDefault();
        axios.post(global.config.BACKEND_URL + "/account/following/newFollower/" + props.username)
            .then(
                setFollowing(true)
                ).catch(
                (error) => {
                    (error.response && error.response.data && error.response.data.message) ?  setErrorMsg(error.response.data.message): setErrorMsg("An unknown error occured"); 
                    alert(errorMsg.toString());
                }
            )
    };

    const unfollow = (event) => {
        event.preventDefault();
        axios.delete(global.config.BACKEND_URL + "/account/following/followerRemoval/"+ props.username)
            .then(
                setFollowing(false)
            ).catch(
                (error) => {
                    error.response ?  setErrorMsg(error.response.data.message): setErrorMsg("An unknown error occured");
                    alert(errorMsg.toString());
                }
            )
    };


    useEffect(() => {

        const isUserFollowing = () => {
            axios.get(global.config.BACKEND_URL + "/account/following/" +props.username).then(
                (response) => {
                    setFollowing(response.data);
                }
            ).catch(
                (error) => {
                  console.log(error.response);
                }
            )
        }

        if(props.currentUser)
        {
            isUserFollowing();
        }
      }, [props.currentUser, isFollowing, props.username])

    return (
            <div className = "follow-button-component">
                    <form onSubmit={isFollowing ? unfollow : follow}>
                    <button className={props.class} type="submit">{isFollowing ? "Unfollow" : "Follow"}</button>
                </form>
            </div>
    );
};
