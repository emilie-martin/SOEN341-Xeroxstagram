import React from "react";
import axios from "axios";
import Post from "./Post";
import { useState, useEffect} from "react";
import '../config';
import './User.scss';

export const User = props => {

    const [errorMsg, setErrorMsg] = useState("");
    const [Pictures, setPictures] = useState([]);
    const [isFollowing, setFollowMsg] = useState(false);

    const follow = (event) => {
        event.preventDefault();
        axios.post(global.config.BACKEND_URL + "/account/following/newFollower/" + props.username)
            .then(
                (response) => {
                    console.log("aaaaaaaaa");
                },
                (error) => {
                    if (error.response)
                    {
                        setErrorMsg("No");
                    } 
                    else
                    {
                        setErrorMsg("An unknown error occurred");
                    }
                }
            )
    };

    const unfollow = (event) => {
        event.preventDefault();
        axios.delete(global.config.BACKEND_URL + "/account/following/newFollower/"+ props.username)
            .then(
                (response) => {
                    props.onSuccess(response);
                },
                (error) => {
                    if (error.response)
                    {
                        setErrorMsg("No");
                    } 
                    else
                    {
                        setErrorMsg("An unknown error occurred");
                    }
                }
            )
    };

    useEffect(() => {

        const getFollowers = () => {
            axios.get(global.config.BACKEND_URL + "/account/profile/profile").then(
                (response) => {
                    console.log(response.data);
                }
            ).catch(
                (error) => {
                    setErrorMsg("Error getting followers");
                }
            )
        }

        const loadUser = () => {
            axios.get(global.config.BACKEND_URL + "/" + props.username + "/pictures").then(
                (response) => {
                    setErrorMsg("");
                    setPictures(response.data);
                }
            ).catch(
                (error) => {
                    setPictures([]);
                    if(error.response && error.response.data && error.response.data.message)
                    {
                        setErrorMsg(error.response.data.message);
                    } 
                    else
                    {
                        setErrorMsg("An unknown error occurred.");
                    }
                }
            )
        }

        getFollowers();
        loadUser();
      }, [props.username])

    return (
        <div className="user-component">
            <form onSubmit={isFollowing ? unfollow : follow}>
                <div className="follow">
                    <button className="follow-button" type="submit">{isFollowing ? "Unfollow" : "Follow"}</button>
                </div>
            </form>
            
            {errorMsg && <div className="error">{errorMsg}</div>}
            {
                Pictures.map((id) => (
                    <div className="single-post" key={id}>
                        <Post id={id}/>
                        <br/>
                    </div>
                ))
            }
        </div>
    );
};

export default User;
