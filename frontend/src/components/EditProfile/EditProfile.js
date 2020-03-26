import "../../config";
import axios from "axios";
import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";

import "./EditProfile.scss";

export default function EditProfile() {
    const [loading, setLoading] = useState(true);
    const [displayName, setDisplayName] = useState("");
    const [biography, setBiography] = useState("");
    const [email, setEmail] = useState("");
    const [birthday, setBirthday] = useState(new Date());
    const [editStatus, setEditStatus] = useState("profile");
    const [originalEmail, setOriginalEmail] = useState("");

    const [editBioSuccess, setEditBioSuccess] = useState(false);
    const [editEmailSuccess, setEditEmailSuccess] = useState(false);
    const [editDisplayNameSuccess, setEditDisplayNameSuccess] = useState(false);
    const [editPasswordSuccess, setEditPasswordSuccess] = useState(false);
    const [editBirthdaySuccess, setEditBirthdaySuccess] = useState(false);
    
    const history = useHistory();
    
    useEffect(() => {
        const fetchCurrentUser = () => {
            axios.get(global.config.BACKEND_URL + "/account")
                .then(
                    (response) => {
                        setDisplayName(response.data.displayName);
                        setBiography(response.data.biography);
                        setEmail(response.data.email);
                        setBirthday(response.data.dateOfBirth);
                        setOriginalEmail(response.data.email);
                        setLoading(false);
                    })
                .catch(() => {
                    history.push("/register");
                })
        }
        fetchCurrentUser();
    }, [loading, history])

    useEffect(() => {
        //This useEffect purpose is to force the page to re-render without having to make an API request
    }, [displayName, biography, email, editStatus, editBioSuccess, editDisplayNameSuccess, editEmailSuccess])

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
            }).then(() => {
                setEditBioSuccess(true);
            }).catch((error) => {
                console.log(error.response.data);
            });

        if (event.target.email.value !== originalEmail) {
            axios.put(global.config.BACKEND_URL + `/account/profile/emailUpdate`,
                {
                    "email": event.target.email.value
                }).then(() => {
                    setEditEmailSuccess(true);
                }).catch((error) => {
                    alert(error.response.data.message);
                })
        }

        axios.put(global.config.BACKEND_URL + `/account/profile/displayNameUpdate`,
            {
                "displayName": event.target.displayName.value
            }).then(() => {
                setEditDisplayNameSuccess(true);
            }).catch((error) => {
                alert(error.response.data.message);
            });
    }
    const updateAccount = (event) => {
        event.preventDefault();
        axios.put(global.config.BACKEND_URL + `/account/passwordModification`,
            {
                "password": event.target.password.value
            }).then(() => {
                setEditPasswordSuccess(true);
            }).catch((error) => {
                alert(error.response.data.message);
            });

        axios.put(global.config.BACKEND_URL + `/account/birthdayModification`,
            {
                "birthday": event.target.birthday.value
            }).then(() => {
                setEditBirthdaySuccess(true);
            }).catch((error) => {
                alert(error.response.data.message);
            })
    }

    return (
        <div className="edit-profile">
            {loading ? "loading"
                    : <div className="edit-profile-page">
                        <div className="sidebar">
                            <nav class="nav">
                                <ul>
                                    <li class="active">Instagram++</li>
                                    <li><a href="#!" onClick={(e) => handleEditStatus(e, "profile")}>Edit Profile</a></li>
                                    <li><a href="#!" onClick={(e) => handleEditStatus(e, "account")} >Edit Account</a></li>
                                    <li><a href="#!" style={{borderRight: "none"}}>Contact Support</a></li>
                                </ul>
                            </nav>
                        </div>
                        {(editStatus === "profile")
                            ? <div className="edit-profile">
                                <form className="edit-profile-form" onSubmit={updateProfile}>
                                    <h1>Edit Profile</h1>
                                    <div>
                                        <label>Display Name</label><br/>
                                        <input type="text" name="displayName" value={displayName} onChange={(e) => handleTyping(e, setDisplayName)}></input>
                                        {editDisplayNameSuccess ? <div className="success">Success</div> : ''}
                                    </div><br/>
                                    <div>
                                        <label>Bio</label><br/>
                                        <textarea name="biography" className="edit-profile-textarea" value={biography} onChange={(e) => handleTyping(e, setBiography)}></textarea>
                                        {editBioSuccess ? <div className="success">Success</div> : ''}
                                    </div><br/>
                                    <div>
                                        <label>Email</label><br/>
                                        <input type="text" name="email" value={email} onChange={(e) => handleTyping(e, setEmail)}></input>
                                        {editEmailSuccess ? <div className="success">Success</div> : ''}
                                    </div>
                                    <div className="edit-profile-button">
                                        <button className="button">Submit</button>
                                    </div><br/>
                                </form>
                             </div>
                            : <div className="edit-profile">
                                <form className="edit-profile-form" onSubmit={updateAccount} >
                                    <h1>Edit Account</h1>
                                    <div>
                                        <label style={{verticalAlign: "middle"}}>Password Modification</label>
                                        <input type="password" name="password"></input>
                                        {editPasswordSuccess ? <div className="success">Success</div> : ''}
                                    </div>
                                    <div>
                                        <label>Birthday</label><br/>
                                        <input type="date" name="birthday" value={birthday} onChange={(e) => handleTyping(e, setBirthday)}></input>
                                        {editBirthdaySuccess ? <div className="success">Success</div> : ''}
                                    </div>
                                    <div className="edit-profile-button">
                                        <button className="button">Submit</button>
                                    </div>
                                </form>
                             </div>
                        }
                    </div>
            }
        </div>
    )
}