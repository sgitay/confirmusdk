package com.confirmu.up.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.confirmu.R;
import com.confirmu.up.ChatActivity;
import com.confirmu.up.api.Constants;
import com.confirmu.up.model.Message;
import com.confirmu.up.utils.Utils;
import com.confirmu.up.utils.ViewAnimationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int RECEIVED = 1;
    private static final int SENT = 2;
    private static final int ASK_AADHAR = 4;
    private static final int ASK_GENDER = 5;
    private static final int ASK_MARITAL = 6;
    private static final int SOCIAL = 7;
    private static final int LIVING = 8;
    private static final int SKILLS = 9;
    private static final int ADD_JOB = 10;
    private static final int REVIEW_INFO = 11;
    private static final int YOU_LIKE = 12;
    private static final int SUBMIT = 13;
    private static final int POLICY = 14;
    private static final int MCQ = 15;
    RecyclerView recyclerView;
    private Activity context;
    private List<Message> arrayList;
    private int lastPosition = -1;

    public ChatAdapter(Activity context, List<Message> arrayList, RecyclerView recyclerView) {
        this.context = context;
        this.arrayList = arrayList;
        this.recyclerView = recyclerView;
    }


    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position).getUserId() == 1) {
            return RECEIVED;
        } else if (arrayList.get(position).getUserId() == ASK_AADHAR) {
            return ASK_AADHAR;
        } else if (arrayList.get(position).getUserId() == ASK_GENDER) {
            return ASK_GENDER;
        } else if (arrayList.get(position).getUserId() == ASK_MARITAL) {
            return ASK_MARITAL;
        } else if (arrayList.get(position).getUserId() == SOCIAL) {
            return SOCIAL;
        } else if (arrayList.get(position).getUserId() == LIVING) {
            return LIVING;
        } else if (arrayList.get(position).getUserId() == SKILLS) {
            return SKILLS;
        } else if (arrayList.get(position).getUserId() == ADD_JOB) {
            return ADD_JOB;
        } else if (arrayList.get(position).getUserId() == REVIEW_INFO) {
            return REVIEW_INFO;
        } else if (arrayList.get(position).getUserId() == YOU_LIKE) {
            return YOU_LIKE;
        } else if (arrayList.get(position).getUserId() == SUBMIT) {
            return SUBMIT;
        } else if (arrayList.get(position).getUserId() == POLICY) {
            return POLICY;
        }else if (arrayList.get(position).getUserId() == MCQ) {
            return MCQ;
        }
        return SENT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        //animation.setDuration(1000);
        animation.setStartOffset(1000);
        switch (viewType) {
            case SENT:
                view = LayoutInflater.from(context).inflate(R.layout.item_chat_right, parent, false);
                //view.startAnimation(animation);
                return new SentMessage(view);
            case ASK_AADHAR:
                view = LayoutInflater.from(context).inflate(R.layout.item_ask_aadhar_info, parent, false);
                view.startAnimation(animation);
                return new AskAadhar(view);
            case ASK_GENDER:
                view = LayoutInflater.from(context).inflate(R.layout.item_ask_gender, parent, false);
                view.startAnimation(animation);
                return new AskGender(view);
            case ASK_MARITAL:
                view = LayoutInflater.from(context).inflate(R.layout.item_ask_marital, parent, false);
                view.startAnimation(animation);
                return new AskMarital(view);
            case SOCIAL:
                view = LayoutInflater.from(context).inflate(R.layout.item_social_accounts, parent, false);
                view.startAnimation(animation);
                return new Social(view);
            case LIVING:
                view = LayoutInflater.from(context).inflate(R.layout.item_for_living, parent, false);
                view.startAnimation(animation);
                return new Living(view);
            case SKILLS:
                view = LayoutInflater.from(context).inflate(R.layout.item_skills_chat, parent, false);
                view.startAnimation(animation);
                return new Skills(view);
            case ADD_JOB:
                view = LayoutInflater.from(context).inflate(R.layout.item_add_job, parent, false);
                view.startAnimation(animation);
                return new AddJob(view);
            case REVIEW_INFO:
                view = LayoutInflater.from(context).inflate(R.layout.dialog_review, parent, false);
                view.startAnimation(animation);
                return new ReviewInfo(view);
            case YOU_LIKE:
                view = LayoutInflater.from(context).inflate(R.layout.item_ask_aadhar_info, parent, false);
                //view.startAnimation(animation);
                return new YouLike(view);
            case SUBMIT:
                view = LayoutInflater.from(context).inflate(R.layout.item_ask_aadhar_info, parent, false);
                view.startAnimation(animation);
                return new Submit(view);
            case POLICY:
                view = LayoutInflater.from(context).inflate(R.layout.item_policy, parent, false);
                view.startAnimation(animation);
                return new Policy(view);

            case MCQ:
                view = LayoutInflater.from(context).inflate(R.layout.item_mcq_list, parent, false);
                view.startAnimation(animation);
                return new MCQ(view);

            case RECEIVED:
                view = LayoutInflater.from(context).inflate(R.layout.item_chat_left, parent, false);
                //Load the animation from the xml file and set it to the row
                view.startAnimation(animation);
                //return row;
                return new ReceivedMessage(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        Message message = arrayList.get(position);
        switch (viewType) {
            case SENT:
                SentMessage sentMessage = (SentMessage) holder;
                sentMessage.tvMessage.setText(message.getMessage());
                if (!((ChatActivity) context).FirstName.equals("")) {
                    sentMessage.tvName.setVisibility(View.VISIBLE);
                    sentMessage.tvName.setText(((ChatActivity) context).FirstName.substring(0, 1).toUpperCase()
                            + ((ChatActivity) context).LastName.substring(0, 1).toUpperCase() + "");
                }
                try {
                    if (Constants.PROFILE_PIC != null) {
                        sentMessage.tvName.setVisibility(View.GONE);
                        sentMessage.ivProfile.setImageURI(Constants.PROFILE_PIC);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sentMessage.ivProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ChatActivity) context).getImage(v);
                    }
                });
                break;
            case ASK_AADHAR:
                AskAadhar askAadhar = (AskAadhar) holder;
                askAadhar.tvYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ((ChatActivity)context).etAnswer.setText("Yes");
//                        ((ChatActivity)context).addAnswerToChat(v);
                        if (!((ChatActivity) context).aadharInfoUpdated) {
                            ((ChatActivity) context).openAadharNumberDialog();
                            ((ChatActivity) context).aadharInfoUpdated = true;
                        }
                    }
                });
                askAadhar.tvNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((ChatActivity) context).aadharInfoUpdated) {
                            ((ChatActivity) context).etAnswer.setText(context.getResources().getString(R.string.no));
                            ((ChatActivity) context).addAnswerToChat(v);
                            ((ChatActivity) context).aadharInfoUpdated = true;
                        }

                    }
                });
                break;
            case ASK_GENDER:
                AskGender askGender = (AskGender) holder;
                askGender.tvMale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((ChatActivity) context).genderSelected) {
                            ((ChatActivity) context).genderSelected = true;
                            ((ChatActivity) context).setGender(context.getResources().getString(R.string.male));
                        }
                    }
                });
                askGender.tvFemale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((ChatActivity) context).genderSelected) {
                            ((ChatActivity) context).genderSelected = true;
                            ((ChatActivity) context).setGender(context.getResources().getString(R.string.female));
                        }
                    }
                });
                break;
            case ASK_MARITAL:
                AskMarital askMarital = (AskMarital) holder;
                askMarital.tvMarried.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((ChatActivity) context).maritalStatus) {
                            ((ChatActivity) context).maritalStatus = true;
                            ((ChatActivity) context).setMarriageStatus(context.getResources().getString(R.string.married));
                        }

                    }
                });
                askMarital.tvSingle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((ChatActivity) context).maritalStatus) {
                            ((ChatActivity) context).maritalStatus = true;
                            ((ChatActivity) context).setMarriageStatus(context.getResources().getString(R.string.single));
                        }
                    }
                });
                askMarital.tvDivorced.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((ChatActivity) context).maritalStatus) {
                            ((ChatActivity) context).maritalStatus = true;
                            ((ChatActivity) context).setMarriageStatus(context.getResources().getString(R.string.divorced));
                        }
                    }
                });
                break;
            case SOCIAL:
                final Social social = (Social) holder;
                social.ivFb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //((ChatActivity)context).etAnswer.setText("Facebook");
                        if (!((ChatActivity) context).FB_LOGIN) {
                            //((ChatActivity) context).socialLogin = true;
                            ((ChatActivity) context).initFBLogin();
                        }
                    }
                });
                social.ivLinkedIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((ChatActivity) context).socialLogin) {
                            //((ChatActivity) context).socialLogin = true;
                            ((ChatActivity) context).linkedInLogin();
                            //((ChatActivity) context).addAnswerToChat(v);
                            social.ivLinkedIn.setOnClickListener(null);
                        }
                    }
                });
                social.ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((ChatActivity) context).socialLogin) {
                            ((ChatActivity) context).socialLogin = true;
                            ((ChatActivity) context).etAnswer.setText(context.getResources().getString(R.string.cancel));
                            ((ChatActivity) context).addAnswerToChat(v);
                            social.ivCancel.setOnClickListener(null);
                            social.ivFb.setOnClickListener(null);
                            social.ivLinkedIn.setOnClickListener(null);
                            ((ChatActivity) context).FB_LOGIN = true;
                        }
                    }
                });
                switch (message.getType()) {
                    case "2":
                        social.ivFb.setVisibility(View.GONE);
                        social.ivLinkedIn.setVisibility(View.VISIBLE);
                        break;
                    case "1":
                        social.ivFb.setVisibility(View.VISIBLE);
                        social.ivLinkedIn.setVisibility(View.GONE);
                        break;
                    default:
                        social.ivLinkedIn.setVisibility(View.VISIBLE);
                        social.ivFb.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case LIVING:
                final Living living = (Living) holder;
                living.tvIndustry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAnimationUtils.expand(living.cvSpinner);
                    }
                });
                living.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAnimationUtils.collapse(living.cvSpinner);
                    }
                });
                living.tvSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if (!((ChatActivity) context).living) {
                        if (living.isValid()) {
                            ((ChatActivity) context).living = true;
                           // ((ChatActivity) context).etAnswer.setText("Living info added.");
                            ((ChatActivity) context).askToAddAnotherJob();
                            ((ChatActivity) context).addIndustryDetail(living.tvIndustry.getText().toString().trim(),
                                    living.etJobTitle.getText().toString().trim(),
                                    living.etCompanyName.getText().toString().trim(),
                                    living.etStartDate.getText().toString().trim(),
                                    living.etEndDate.getText().toString().trim(),
                                    living.cbCurrent.isChecked() + "");

                            living.tvIndustry.setOnClickListener(null);
                            living.etJobTitle.setEnabled(false);
                            living.etCompanyName.setEnabled(false);
                            living.etStartDate.setEnabled(false);
                            living.etEndDate.setEnabled(false);
                            living.cbCurrent.setEnabled(false);

                            living.tvSave.setOnClickListener(null);
                            living.tvCancel.setOnClickListener(null);

                            living.tvSave.setEnabled(false);
                            living.tvCancel.setEnabled(false);
                        }
                        //  }

                    }
                });

                living.tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ChatActivity) context).living = true;
                        ((ChatActivity) context).etAnswer.setText("Living info not added.");
                        ((ChatActivity) context).addAnswerToChat(v);
                        //living.view.setVisibility(View.VISIBLE);

                        living.tvIndustry.setOnClickListener(null);
                        living.etJobTitle.setEnabled(false);
                        living.etCompanyName.setEnabled(false);
                        living.etStartDate.setEnabled(false);
                        living.etEndDate.setEnabled(false);
                        living.cbCurrent.setEnabled(false);

                        living.tvSave.setOnClickListener(null);
                        living.tvCancel.setOnClickListener(null);

                        living.tvSave.setEnabled(false);
                        living.tvCancel.setEnabled(false);
                    }
                });

                living.cbCurrent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            living.etEndDate.setEnabled(false);
                            living.etEndDate.setText("");
                        } else {
                            living.etEndDate.setEnabled(true);
                        }
                    }
                });

                break;

            case SKILLS:
                Skills skills = (Skills) holder;
                ChipsLayoutManager spanLayoutManager = ChipsLayoutManager.newBuilder(context)
                        .setOrientation(ChipsLayoutManager.HORIZONTAL)
                        .setRowStrategy(ChipsLayoutManager.STRATEGY_CENTER_DENSE)
                        .build();
                skills.recyclerView.setLayoutManager(spanLayoutManager);
                /*skills.recyclerView.addItemDecoration(new SpacingItemDecoration(context.getResources().getDimensionPixelOffset(R.dimen.item_space_small),
                        context.getResources().getDimensionPixelOffset(R.dimen.item_space_small)));*/
                skills.recyclerView.setAdapter(new SkillsChatAdapter(((ChatActivity) context), message.getList()));

                if (!((ChatActivity) context).FirstName.equals("")) {
                    skills.tvName.setVisibility(View.VISIBLE);
                    skills.tvName.setText(((ChatActivity) context).FirstName.substring(0, 1).toUpperCase()
                            + ((ChatActivity) context).LastName.substring(0, 1).toUpperCase() + "");
                }
                try {
                    if (Constants.PROFILE_PIC != null) {
                        skills.tvName.setVisibility(View.GONE);
                        skills.ivProfile.setImageURI(Constants.PROFILE_PIC);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                skills.ivProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ChatActivity) context).getImage(v);
                    }
                });
                break;

            case ADD_JOB:
                final AddJob addJob = (AddJob) holder;
                addJob.tvYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
                        /*((ChatActivity) context).etAnswer.setText("Yes");
                        ((ChatActivity) context).addAnswerToChat(v);*/
                        ((ChatActivity) context).addAnotherJob();
                        addJob.tvYes.setOnClickListener(null);
                        addJob.tvNo.setOnClickListener(null);

                    }
                });
                addJob.tvNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ChatActivity) context).etAnswer.setText(context.getResources().getString(R.string.no));
                        ((ChatActivity) context).addAnswerToChat(v);
                        addJob.tvNo.setOnClickListener(null);
                        addJob.tvYes.setOnClickListener(null);

                    }
                });
                addJob.tvCheckJobs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ChatActivity) context).checkJobs();
                    }
                });
                break;
            case REVIEW_INFO:
                ReviewInfo reviewInfo = (ReviewInfo) holder;
                reviewInfo.tvName.setText(((ChatActivity) context).FirstName + " " + ((ChatActivity) context).LastName);
                reviewInfo.tvEmail.setText(((ChatActivity) context).EmailAddress);
                reviewInfo.tvAge.setText(((ChatActivity) context).AGE);
                reviewInfo.tvMarried.setText(((ChatActivity) context).MaritalStatus);
                reviewInfo.tvGender.setText(((ChatActivity) context).Gender);
                reviewInfo.tvKids.setText(((ChatActivity) context).KIDS);
                break;
            case YOU_LIKE:
                YouLike youLike = (YouLike) holder;
                youLike.tvYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
                        /*((ChatActivity) context).etAnswer.setText("Yes");
                        ((ChatActivity) context).addAnswerToChat(v);*/
                        if (!((ChatActivity) context).Like) {
                            ((ChatActivity) context).submitInfo();
                            ((ChatActivity) context).Like = true;
                        }

                    }
                });
                youLike.tvNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* ((ChatActivity) context).etAnswer.setText(getResources().getString(R.string.no));
                        ((ChatActivity) context).addAnswerToChat(v);*/

                    }
                });
                break;

            case SUBMIT:
                Submit submit = (Submit) holder;
                submit.tvYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
                        /*((ChatActivity) context).etAnswer.setText("Yes");
                        ((ChatActivity) context).addAnswerToChat(v);*/
                        if (!((ChatActivity) context).Submitted) {
                            ((ChatActivity) context).postPersonalData();
                            ((ChatActivity) context).Submitted = true;
                        }

                    }
                });
                submit.tvNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* ((ChatActivity) context).etAnswer.setText(getResources().getString(R.string.no));
                        ((ChatActivity) context).addAnswerToChat(v);*/

                    }
                });
                break;

            case POLICY:
                Policy policy = (Policy) holder;
                policy.tv_policy.setText(((ChatActivity) context).LOAN_NAME + "\n" + context.getString(R.string.policy));
                break;

            case MCQ:
                MCQ mcq = (MCQ) holder;
                mcq.recyclerView.setAdapter(new MCQsAdapter(context, message.getMcqArray(), this));
                break;

            default:

                Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
                //animation.setDuration(1000);
                animation.setStartOffset(1000);
                ReceivedMessage receivedMessage = (ReceivedMessage) holder;
                if (message.getType().equals("clickable")) {
                    setClickableSpan(receivedMessage.tvMessage);
                } else {
                    receivedMessage.tvMessage.setText(message.getMessage());
                }
                if (message.getType().equals("2")) {
                    receivedMessage.ivProfile.setVisibility(View.INVISIBLE);
                } else {
                    receivedMessage.ivProfile.setVisibility(View.VISIBLE);
                }
                /*try {
                    if (recyclerView.findViewHolderForAdapterPosition(position-1) instanceof ReceivedMessage){
                        receivedMessage.ivProfile.setVisibility(View.INVISIBLE);
                    }else {
                        receivedMessage.ivProfile.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                if (position > lastPosition)
                {
                    receivedMessage.mainLayout.startAnimation(animation);
                    lastPosition = position;
                }

                break;

        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void setClickableSpan(TextView textView) {
        SpannableString ss = new SpannableString(context.getResources().getString(R.string.job_skills));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (!((ChatActivity) context).skills) {
                    ((ChatActivity) context).skills = true;
                    ((ChatActivity) context).openSelectSkillsDialog();
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(context.getResources().getColor(R.color.black));

            }
        };
        ss.setSpan(clickableSpan, 49, 72, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
    }

    private boolean isValidDate(String date) {
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(date, ".");
            String month = stringTokenizer.nextToken();
            String year = stringTokenizer.nextToken();
            return Integer.parseInt(month) > 0 && Integer.parseInt(month) < 13 && Integer.parseInt(year) < 2019;
        } catch (Exception e) {
            return false;
        }
    }

    private static class SentMessage extends RecyclerView.ViewHolder {

        TextView tvMessage, tvName;
        CircleImageView ivProfile;

        SentMessage(View viewHolder) {
            super(viewHolder);
            tvMessage = (TextView) viewHolder.findViewById(R.id.txtMessage);
            tvName = (TextView) viewHolder.findViewById(R.id.tv_name);
            ivProfile = (CircleImageView) viewHolder.findViewById(R.id.iv_profile);
        }

    }

    private static class ReceivedMessage extends RecyclerView.ViewHolder {

        TextView tvMessage;
        CircleImageView ivProfile;
        LinearLayout mainLayout;

        ReceivedMessage(View viewHolder) {
            super(viewHolder);
            tvMessage = (TextView) viewHolder.findViewById(R.id.txtMessage);
            ivProfile = (CircleImageView) viewHolder.findViewById(R.id.iv_profile);
            mainLayout = (LinearLayout) viewHolder.findViewById(R.id.content);
        }

    }

    private static class AskAadhar extends RecyclerView.ViewHolder {

        TextView tvYes, tvNo;

        AskAadhar(View viewHolder) {
            super(viewHolder);
            tvYes = (TextView) viewHolder.findViewById(R.id.tv_yes);
            tvNo = (TextView) viewHolder.findViewById(R.id.tv_no);
        }

    }

    private static class AskGender extends RecyclerView.ViewHolder {

        TextView tvMale, tvFemale;

        AskGender(View viewHolder) {
            super(viewHolder);
            tvMale = (TextView) viewHolder.findViewById(R.id.tv_male);
            tvFemale = (TextView) viewHolder.findViewById(R.id.tv_female);
        }

    }

    private static class AskMarital extends RecyclerView.ViewHolder {

        TextView tvMarried, tvSingle, tvDivorced;

        AskMarital(View viewHolder) {
            super(viewHolder);
            tvMarried = (TextView) viewHolder.findViewById(R.id.tv_married);
            tvSingle = (TextView) viewHolder.findViewById(R.id.tv_single);
            tvDivorced = (TextView) viewHolder.findViewById(R.id.tv_divorced);
        }

    }

    private static class Social extends RecyclerView.ViewHolder {

        ImageView ivFb, ivLinkedIn, ivCancel;

        Social(View viewHolder) {
            super(viewHolder);
            ivFb = (ImageView) viewHolder.findViewById(R.id.iv_fb);
            ivLinkedIn = (ImageView) viewHolder.findViewById(R.id.iv_linkedin);
            ivCancel = (ImageView) viewHolder.findViewById(R.id.iv_cancel);
        }

    }

    private static class Skills extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        CircleImageView ivProfile;
        TextView tvName;

        Skills(View viewHolder) {
            super(viewHolder);
            recyclerView = (RecyclerView) viewHolder.findViewById(R.id.rv_skills);
            ivProfile = (CircleImageView) viewHolder.findViewById(R.id.iv_profile);
            tvName = (TextView) viewHolder.findViewById(R.id.tv_name);

        }

    }

    private static class AddJob extends RecyclerView.ViewHolder {

        TextView tvYes, tvNo, tvCheckJobs;

        AddJob(View viewHolder) {
            super(viewHolder);
            tvYes = (TextView) viewHolder.findViewById(R.id.tv_yes);
            tvNo = (TextView) viewHolder.findViewById(R.id.tv_no);
            tvCheckJobs = (TextView) viewHolder.findViewById(R.id.tv_check_jobs);
        }

    }

    private static class ReviewInfo extends RecyclerView.ViewHolder {

        TextView tvName, tvEmail, tvGender, tvMarried, tvAge, tvKids;

        ReviewInfo(View viewHolder) {
            super(viewHolder);

            tvName = (TextView) viewHolder.findViewById(R.id.tv_name);
            tvEmail = (TextView) viewHolder.findViewById(R.id.tv_email);
            tvGender = (TextView) viewHolder.findViewById(R.id.tv_gender);
            tvMarried = (TextView) viewHolder.findViewById(R.id.tv_married);
            tvAge = (TextView) viewHolder.findViewById(R.id.tv_age);
            tvKids = (TextView) viewHolder.findViewById(R.id.tv_kids);
        }

    }

    private static class YouLike extends RecyclerView.ViewHolder {

        TextView tvYes, tvNo;

        YouLike(View viewHolder) {
            super(viewHolder);
            tvYes = (TextView) viewHolder.findViewById(R.id.tv_yes);
            tvNo = (TextView) viewHolder.findViewById(R.id.tv_no);
        }

    }

    private static class Submit extends RecyclerView.ViewHolder {

        TextView tvYes, tvNo;

        Submit(View viewHolder) {
            super(viewHolder);
            tvYes = (TextView) viewHolder.findViewById(R.id.tv_yes);
            tvNo = (TextView) viewHolder.findViewById(R.id.tv_no);
        }

    }

    private static class Policy extends RecyclerView.ViewHolder {

        TextView tv_policy;

        Policy(View viewHolder) {
            super(viewHolder);
            tv_policy = (TextView) viewHolder.findViewById(R.id.tv_policy);
        }

    }

    private class MCQ extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;

        MCQ(View viewHolder) {
            super(viewHolder);
            recyclerView = (RecyclerView) viewHolder.findViewById(R.id.recycler_view);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        }

    }

    public class Living extends RecyclerView.ViewHolder {
        RecyclerView rvLoans;
        TextView tvIndustry, tvSave, tvCancel;
        CardView cvSpinner;
        Button view;
        EditText etJobTitle, etCompanyName, etStartDate, etEndDate;
        CheckBox cbCurrent;

        String dateRegex = "^\\d{2}.\\d{4}$";

        Living(View viewHolder) {
            super(viewHolder);
            tvIndustry = (TextView) viewHolder.findViewById(R.id.tv_industry);
            tvSave = (TextView) viewHolder.findViewById(R.id.tv_save_position);
            tvCancel = (TextView) viewHolder.findViewById(R.id.tv_cancel);

            etJobTitle = (EditText) viewHolder.findViewById(R.id.et_job_title);
            etCompanyName = (EditText) viewHolder.findViewById(R.id.et_company_name);
            etStartDate = (EditText) viewHolder.findViewById(R.id.et_start_date);
            etEndDate = (EditText) viewHolder.findViewById(R.id.et_end_date);

            cbCurrent = (CheckBox) viewHolder.findViewById(R.id.cb_current);

            cvSpinner = (CardView) viewHolder.findViewById(R.id.cv_spinner);
            rvLoans = (RecyclerView) viewHolder.findViewById(R.id.rv_loans);
            view = (Button) viewHolder.findViewById(R.id.view);

            rvLoans.setNestedScrollingEnabled(true);
            rvLoans.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            List<String> industries = new ArrayList<>();
            industries.add("Defence & Space");
            industries.add("Computer Hardware");
            industries.add("Computer Software");
            industries.add("Computer Networking");
            industries.add("Internet");
            industries.add("Semiconductors");
            industries.add("Telecommunications");
            industries.add("Law Practice");
            industries.add("Legal Services");
            industries.add("Management Consulting");
            industries.add("Biotechnology");
            industries.add("Medical Practice");
            industries.add("Hospital & Healthcare");
            industries.add("Pharmaceuticals");
            industries.add("Veterinary");
            industries.add("Cosmetics");

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewAnimationUtils.collapse(cvSpinner);
                }
            };


            rvLoans.setAdapter(new IndustriesAdapter(context, industries, onClickListener, this));


        }

        public void hideSpinner() {
            ViewAnimationUtils.collapse(cvSpinner);
        }


        public boolean isValid() {
            if (etCompanyName.getText().toString().trim().equals("")) {
                Utils.showMessage(context, "Please fill all fields");
                return false;
            } else if (etJobTitle.getText().toString().trim().equals("")) {
                Utils.showMessage(context, "Please fill all fields");
                return false;
            } else if (etStartDate.getText().toString().trim().equals("")) {
                Utils.showMessage(context, "Please fill all fields");
                return false;
            } else if (!etStartDate.getText().toString().trim().matches(dateRegex)) {
                Utils.showMessage(context, "Date format should be MM.YYYY");
                return false;
            } else if (!isValidDate(etStartDate.getText().toString().trim())) {
                Utils.showMessage(context, "Please enter valid start date");
                return false;
            } else if (etEndDate.getText().toString().trim().equals("") && !cbCurrent.isChecked()) {
                Utils.showMessage(context, "Please fill all fields");
                return false;
            } else if (!etEndDate.getText().toString().trim().matches(dateRegex) && !cbCurrent.isChecked()) {
                Utils.showMessage(context, "Date format should be MM.YYYY");
                return false;
            } else if (!isValidDate(etEndDate.getText().toString().trim()) && !cbCurrent.isChecked()) {
                Utils.showMessage(context, "Please enter valid end date");
                return false;
            } else {
                return true;
            }
        }


    }

    public void setAnswer(String answer){
        ((ChatActivity) context).etAnswer.setText(answer);
        ((ChatActivity) context).addAnswerToChat(null);
    }


}