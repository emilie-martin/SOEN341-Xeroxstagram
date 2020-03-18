import '../../config';

import axios from 'axios'
import React, { useState, useEffect } from 'react'
import { Redirect } from 'react-router-dom';
import './EditProfile.scss'

export default function EditProfile() {
    const [loading, setLoading] = useState(true);
    const [displayName, setDisplayName] = useState("");
    const [biography, setBiography] = useState("");
    const [email, setEmail] = useState("");
    const [birthday, setBirthday] = useState(new Date());
    const [editStatus, setEditStatus] = useState("profile");

    useEffect(() => {
        const fetchCurrentUser = () => {
            axios.get(global.config.BACKEND_URL + "/account").then(
                (response) => {
                    setDisplayName(response.data.displayName);
                    setBiography(response.data.biography);
                    setEmail(response.data.email);
                    setBirthday(response.data.dateOfBirth);
                    setLoading(false);
                }).catch(() => {
                    return <Redirect to="/register"></Redirect>
                })
        }
        fetchCurrentUser();
    }, [loading])

    useEffect(() => {
        //This useEffect purpose is to force the page to re-render without having to make an API request
    }, [displayName, biography, email, editStatus])

    const handleTyping = (e, setterFunction) => {
        setterFunction(e.target.value);
    }

    const handleEditStatus = (e, status) => {
        e.preventDefault();
        setEditStatus(status);
    }

    const updateProfile = (event) => {
        event.preventDefault();
        axios.put(global.config.BACKEND_URL + `/account/profile/biographyUpdate`,
            {
                "biography": event.target.biography.value
            });

        axios.put(global.config.BACKEND_URL + `/account/profile/emailUpdate`,
            {
                "email": event.target.email.value
            }).catch((error) => {
                alert(error.response.data.message);
            })

        axios.put(global.config.BACKEND_URL + `/account/profile/displayNameUpdate`,
            {
                "displayName": event.target.displayName.value
            }).catch((error) => {
                alert(error.response.data.message);
            });
    }
    const updateAccount = (event) => {
        event.preventDefault();
        axios.put(global.config.BACKEND_URL + `/account/passwordModification`,
            {
                "password": event.target.password.value
            }).catch((error) => {
                alert(error.response.data.message);
            });

        axios.put(global.config.BACKEND_URL + `/account/birthdayModification`,
            {
                "birthday": event.target.birthday.value
            }).catch((error) => {
                alert(error.response.data.message);
            })
    }

    return (
        <div>
            {
                loading ? 'loading' :
                    <div className="edit-profile-page">
                        <div className="sidebar">
                            <nav class="nav">
                                <ul>
                                    <li class="active"><a href="#!">Instagram++</a></li>
                                    <li><a href="#!" onClick={(e) => handleEditStatus(e, "profile")}>Edit Profile</a></li>
                                    <li><a href="#!" onClick={(e) => handleEditStatus(e, "account")} >Edit Account</a></li>
                                    <li><a href="#!">Contact Support</a></li>
                                </ul>
                            </nav>
                        </div>
                        {
                            (editStatus === "profile") ?

                                <div className="edit-profile">
                                    <form className="edit-profile-form" onSubmit={updateProfile}>
                                        <h1>Public Profile</h1>
                                        <div>
                                            <label>Display Name</label>
                                            <input type="text" name="displayName" value={displayName} onChange={(e) => handleTyping(e, setDisplayName)}></input>
                                        </div>
                                        <br />
                                        <div>
                                            <label>Bio</label>
                                            <textarea name="biography" className="edit-profile-textarea" value={biography} onChange={(e) => handleTyping(e, setBiography)}></textarea>
                                        </div>
                                        <br />
                                        <div>
                                            <label>Email</label>
                                            <input type="text" name="email" value={email} onChange={(e) => handleTyping(e, setEmail)}></input>
                                        </div>
                                        <div className="edit-profile-button">
                                            <button className="btn">Submit</button>
                                        </div>
                                        <br />
                                    </form>
                                </div>
                                :
                                <div className="edit-profile">
                                    <form className="edit-profile-form" onSubmit={updateAccount} >
                                        <h1>Edit Account</h1>
                                        <div>
                                            <label>Password Modification</label>
                                            <input type="password" name="password"></input>
                                        </div>
                                        <br />
                                        <div>
                                            <label>Birthday</label>
                                            <input type="date" name="birthday" value={birthday} onChange={(e) => handleTyping(e, setBirthday)}></input>
                                        </div>
                                        <div className="edit-profile-button">
                                            <button className="btn">Submit</button>
                                        </div>
                                        <br />
                                    </form>
                                </div>
                        }
                    </div>
            }
        </div>
    )
}