import React, { useState, useEffect } from 'react'
import axios from "axios";
import '../config';

export const FollowingButton = (props) =>
{
    const[isLoggedIn, setLoggedIn] = useState(false);
    const [isFollowing, setFollowing] = useState(false);
    const [errorMsg, setErrorMsg] = useState("");

    const follow = (event) => {
        event.preventDefault();
        axios.post(global.config.BACKEND_URL + "/account/following/newFollower/" + props.username)
            .then(
                setFollowing(true),
                (error) => {
                    (error.response && error.response.data && error.response.data.message) ?  setErrorMsg(error.response.data.message): setErrorMsg("An unknown error occured"); 
                    alert(errorMsg);
                }
            )
    };

    const unfollow = (event) => {
        event.preventDefault();
        axios.delete(global.config.BACKEND_URL + "/account/following/followerRemoval/"+ props.username)
            .then(
                setFollowing(false),
                (error) => {
                    error.response ?  setErrorMsg(error.response.data.message): setErrorMsg("An unknown error occured");
                }
            )
    };

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

    useEffect(() => {

        const isUserLoggedIn = () => {
            axios.get(global.config.BACKEND_URL + "/account").then(
                (response) => {
                    //console.log(response.data);
                    setLoggedIn(true);
                    isUserFollowing();
                }
            ).catch(() => { setLoggedIn(false)})
        };

        isUserLoggedIn();

      }, [isLoggedIn, isFollowing])

    return (
            <div>
                <form onSubmit={isFollowing ? unfollow : follow}>
                <div className="follow">
                    <button className="follow-button" type="submit">{isFollowing ? "Unfollow" : "Follow"}</button>
                </div>
                </form>
            </div>
    );
};

export default FollowingButton;
